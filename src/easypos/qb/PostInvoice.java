package easypos.qb;

import org.json.*;
import inqb.*;
import easypos.qb.wrapper.*;

public class PostInvoice extends PostBase {
	
	@Override
	protected IPost CreateHeader() {
		// TODO Auto-generated method stub
		return new MyInvoice();
	}

	@Override
	protected void HandleLine(IPost header, JSONObject line) throws Exception {
		// TODO Auto-generated method stub
		InvoiceItem invLine = new InvoiceItem();
		invLine.setItemName(line.getString("item"));
		invLine.setAmount(line.getString("amt"));
		invLine.setQuantity(line.getString("qty"));
		((Invoice)header).getLineItems().add(invLine);
	}

	@Override
	protected void HandleHeader(IPost header, JSONObject h) throws Exception {
		// TODO Auto-generated method stub
		Invoice inv = ((Invoice)header);
		inv.setCustomerName(h.getString("cust"));
	}
}
