LOAD DATA
CHARACTERSET JA16SJIS
INFILE './dataonly_backup.csv'
TRUNCATE INTO TABLE ABC
FIELDS TERMINATED BY ','
TRAILING NULLCOLS
(XXXXX,XXXXY,XXXXZ)
