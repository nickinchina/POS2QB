package easypos.qb;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import easypos.qb.wrapper.*;
import inqb.*;

public abstract class PostBase {
	private String qburl;
	protected static final String InQblicense = "42424736564131535542524131535542434744393335323100000000000000000000000000000000324243385847333600004A34364838333558463253500000";
    // This is a write property
    public void setQBUrl(String value) {
    	qburl = value;
    }
    
    public String getQBUrl() {
        return qburl;
    }
    
    protected Boolean IsStringEmpty(String s)
    {
    	if (s == null || s.trim().equals(""))
    		return true;
    	else
    		return false;
    }
	
    protected void PrepareEntry(
    	IPost postObj, String refid, String refno, String date) throws Exception
    {
    	postObj.setRuntimeLicense(InQblicense);
    	postObj.setQBConnectionString(this.getQBUrl());
	    if (!IsStringEmpty(refid))
	    {
	    	postObj.get(refid);
	    	postObj.delete();
	    }
	    postObj.reset();
	    postObj.setRefNumber(refno);
	    postObj.setTransactionDate(date);
    }
    
    protected void AddAccount(String name, int accountType) throws Exception
	{
		Account a = new Account();
		try {
			a.setRuntimeLicense(InQblicense);
			a.setQBConnectionString(this.getQBUrl());
			a.getByName(name);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			a.reset();
			a.setAccountName(name);
			a.setAccountType(accountType);
			a.add();
		}
	}
    
    protected abstract IPost CreateHeader();
    protected abstract void HandleHeader(IPost header, JSONObject h) throws Exception;
    protected abstract void HandleLine(IPost header, JSONObject line) throws Exception;
    
    public String CreateEntry(String JSON) throws Exception
	{	
		JSONTokener jsonParser = new JSONTokener(JSON);    
	    JSONObject je = (JSONObject) jsonParser.nextValue();  
	    JSONArray lines = je.getJSONArray("lines");  
	    
	    IPost qje = this.CreateHeader();
	    String refid = je.getString("refid");
	    this.PrepareEntry(qje, refid, je.getString("ref"), je.getString("date"));
	    this.HandleHeader(qje, je);
		int i;
		for (i=0; i<lines.length(); i++) {
			JSONObject jeline = lines.getJSONObject(i);
			this.HandleLine(qje, jeline);
		}
		qje.add();
		return qje.getRefId();
	}
}

