-- sample query with a parameter
-- test http://localhost:8080/vertica-rs/api/repo/cust.byId/2
select * from customers where CustID = ?
