package easypos.qb.wrapper;

public interface IPost {
	void setRuntimeLicense(String license) throws inqb.InQBException;
	void setQBConnectionString(String license) throws inqb.InQBException;
	void reset() throws inqb.InQBException;
	void delete() throws inqb.InQBException;
	void add() throws inqb.InQBException;
	void get(String refid) throws inqb.InQBException;
	void setRefNumber(String refNo) throws inqb.InQBException;
	void setTransactionDate(String refNo) throws inqb.InQBException;
	String getRefId() throws inqb.InQBException;
	
}
