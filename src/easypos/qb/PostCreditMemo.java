package easypos.qb;

import org.json.*;

import inqb.*;
import easypos.qb.wrapper.*;

public class PostCreditMemo extends PostBase {

	@Override
	protected IPost CreateHeader() {
		// TODO Auto-generated method stub
		return new MyCreditmemo();
	}

	@Override
	protected void HandleHeader(IPost header, JSONObject h) throws Exception {
		// TODO Auto-generated method stub
		Creditmemo inv = ((Creditmemo)header);
		inv.setCustomerName(h.getString("cust"));
	}

	@Override
	protected void HandleLine(IPost header, JSONObject line) throws Exception {
		// TODO Auto-generated method stub
		CreditMemoItem invLine = new CreditMemoItem();
		invLine.setItemName(line.getString("item"));
		invLine.setAmount(line.getString("amt"));
		invLine.setQuantity(line.getString("qty"));
		((Creditmemo)header).getLineItems().add(invLine);
	}

}
