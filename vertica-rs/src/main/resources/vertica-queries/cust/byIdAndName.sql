-- sample query with 2 parameter
-- test http://localhost:8080/vertica-rs/api/repo/cust.byIdAndName/2/Brown
select * from customers where CustID = ? and Last_Name like ?
