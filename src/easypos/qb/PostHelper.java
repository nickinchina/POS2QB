package easypos.qb;

import org.json.*;
import org.apache.commons.*;
import android.R.string;
import inqb.*;

public class PostHelper 
{
	private String qburl;
	private static final String InQblicense = "42424736564131535542524131535542434744393335323100000000000000000000000000000000324243385847333600004A34364838333558463253500000";
    // This is a write property
    public void setQBUrl(String value) {
    	qburl = value;
    }
    
    public String getQBUrl() {
        return qburl;
    }
    
    private Boolean IsStringEmpty(String s)
    {
    	if (s == null || s.trim().equals(""))
    		return true;
    	else
    		return false;
    }
    
	public String CreateJournalEntry(String JSON) throws Exception
	{	
		JSONTokener jsonParser = new JSONTokener(JSON);    
	    JSONObject je = (JSONObject) jsonParser.nextValue();  
	    JSONArray lines = je.getJSONArray("lines");  
	    
	    Journalentry qje = new Journalentry();
	    qje.setRuntimeLicense(InQblicense);
	    qje.setQBConnectionString(this.getQBUrl());
	    String refid = je.getString("refid");
	    if (!IsStringEmpty(refid))
	    {
	    	qje.get(refid);
	    	qje.delete();
	    }
	    qje.reset();
		qje.setRefNumber(je.getString("ref"));
		qje.setTransactionDate(je.getString("date"));
		int i;
		for (i=0; i<lines.length(); i++) {
			JSONObject jeline = lines.getJSONObject(i);
		    JournalLine qjl = new inqb.JournalLine();
		    
		    String act = jeline.getString("account");
		    this.AddAccount(act, jeline.getInt("atype"));
		    qjl.setAccountName(act);
		    qjl.setMemo(jeline.getString("memo"));
		    String className = jeline.getString("class");
		    if (!IsStringEmpty(className))
		    	qjl.setClassName(className);
		    qjl.setLineType(jeline.getInt("dir"));
		    qjl.setAmount(jeline.getString("amt"));
		    String ent = jeline.getString("ent");
		    if (!IsStringEmpty(ent))
		    	qjl.setEntityName(ent);
		    
		    qje.getJournalLines().add(qjl);
		}
		qje.add();
		return qje.getRefId();
	}
	
	private void AddAccount(String name, int accountType) throws Exception
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
	
}
