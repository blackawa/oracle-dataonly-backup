set head off
set trimspool on
set pagesize 0
set termout off
set feedback off
set echo off

spool dataonly_backup.csv

select 
  XXXXX || ',' ||
  XXXXY || ',' ||
  XXXXZ
from ABC;

spool off
exit
