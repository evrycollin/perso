-- sample query with a parameter
-- test http://localhost:8080/vertica-rs/api/repo/cust.byName/Brown
-- test http://localhost:8080/vertica-rs/api/repo/cust.byName/%25row%25
select * from customers where Last_Name like ?
