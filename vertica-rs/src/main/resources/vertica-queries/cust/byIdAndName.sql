-- http://localhost:8080/vertica-rs/api/query/cust.byIdAndName/[id]/[name]
select * from customers where CustID = ? and Last_Name like ?
