package easypos.qb;

import org.json.*;

import inqb.*;
import easypos.qb.wrapper.*;

public class PostJournalentry extends PostBase
{
	@Override
	protected IPost CreateHeader() {
		// TODO Auto-generated method stub
		return new MyJournalentry();
	}

	@Override
	protected void HandleLine(IPost header, JSONObject jeline) throws Exception {
		// TODO Auto-generated method stub
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
	    
	    ((Journalentry)header).getJournalLines().add(qjl);
	}

	@Override
	protected void HandleHeader(IPost header, JSONObject h) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
