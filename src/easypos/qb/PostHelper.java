package easypos.qb;

import org.json.*;

import android.R.string;
import inqb.*;

public class PostHelper 
{
	public String AddJournalEntry(String JSON) 
	{	
	    try {
	    	JSONTokener jsonParser = new JSONTokener(JSON);    
		    JSONObject je = (JSONObject) jsonParser.nextValue();  
		    JSONArray lines = je.getJSONArray("lines");  

		    Journalentry qje = new Journalentry();
		    String refid = je.getString("refid");
		    if (refid != "")
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
			    qjl.setClassName(jeline.getString("class"));
			    qjl.setLineType(jeline.getInt("dir"));
			    qjl.setAmount(jeline.getString("amt"));
			    String ent = jeline.getString("ent");
			    if (ent != "")
			    	qjl.setEntityName(ent);
			    
			    qje.getJournalLines().add(qjl);
			}
			qje.add();
			return qje.getRefId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}
	}
	
	private void AddAccount(String name, int accountType)
	{
		Account a = new Account();
		try {
			a.getByName(name);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				a.reset();
				a.setAccountName(name);
				a.setAccountType(accountType);
				a.add();
			} catch (InQBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
