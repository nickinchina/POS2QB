package easypos.qb;
import org.json.JSONObject;

import inqb.*;
import easypos.qb.wrapper.*;

public class PostReceivepayment extends PostBase {

	@Override
	protected IPost CreateHeader() {
		// TODO Auto-generated method stub
		return new MyReceivepayment();
	}

	@Override
	protected void HandleHeader(IPost header, JSONObject h) throws Exception {
		// TODO Auto-generated method stub
		Receivepayment inv = ((Receivepayment)header);
		inv.setCustomerName(h.getString("cust"));
		inv.setAmount(h.getString("amt"));
	}

	@Override
	protected void HandleLine(IPost header, JSONObject line) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
