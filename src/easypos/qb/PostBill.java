package easypos.qb;

import org.json.*;
import inqb.*;
import easypos.qb.wrapper.*;

public class PostBill extends PostBase {
	
	@Override
	protected IPost CreateHeader() {
		// TODO Auto-generated method stub
		return new MyBill();
	}

	@Override
	protected void HandleLine(IPost header, JSONObject line) throws Exception {
		// TODO Auto-generated method stub
		StandardItem invLine = new StandardItem();
		invLine.setItemName(line.getString("item"));
		invLine.setAmount(line.getString("amt"));
		invLine.setQuantity(line.getString("qty"));
		((Bill)header).getLineItems().add(invLine);
	}

	@Override
	protected void HandleHeader(IPost header, JSONObject h) throws Exception {
		// TODO Auto-generated method stub
		Bill inv = ((Bill)header);
		inv.setVendorName(h.getString("vendor"));
	}
}
