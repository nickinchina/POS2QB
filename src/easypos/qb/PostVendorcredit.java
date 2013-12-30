package easypos.qb;

import org.json.*;
import inqb.*;
import easypos.qb.wrapper.*;

public class PostVendorcredit extends PostBase {
	
	@Override
	protected IPost CreateHeader() {
		// TODO Auto-generated method stub
		return new MyVendorcredit();
	}

	@Override
	protected void HandleLine(IPost header, JSONObject line) throws Exception {
		// TODO Auto-generated method stub
		StandardItem invLine = new StandardItem();
		invLine.setItemName(line.getString("item"));
		invLine.setAmount(line.getString("amt"));
		invLine.setQuantity(line.getString("qty"));
		((Vendorcredit)header).getLineItems().add(invLine);
	}

	@Override
	protected void HandleHeader(IPost header, JSONObject h) throws Exception {
		// TODO Auto-generated method stub
		Vendorcredit inv = ((Vendorcredit)header);
		inv.setVendorName(h.getString("vendor"));
	}
}
