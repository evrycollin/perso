-- http://localhost:8080/vertica-rs/api/query/cust.byName/[name]
-- http://localhost:8080/vertica-rs/api/repo/cust.byName/%25[likeName]%25
select * from customers where Last_Name like ?
