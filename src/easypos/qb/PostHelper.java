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
			qje.setRefNumber(je.getString("ref"));
			qje.setTransactionDate(je.getString("date"));
			int i;
			for (i=0; i<lines.length(); i++) {
				JSONObject jeline = lines.getJSONObject(i);
			    JournalLine qjl = new inqb.JournalLine();
			    qjl.setAccountName(jeline.getString("account"));
			    qjl.setMemo(jeline.getString("memo"));
			    qjl.setClassName(jeline.getString("class"));
			    qjl.setLineType(jeline.getInt("dir"));
			    qjl.setAmount(jeline.getString("amt"));
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
}
