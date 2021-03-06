#! /bin/bash
# index_all_sirsi.sh
# Import all marc files from sirsi full dump into a Solr index  (Profiling flavor)
#
# updated for Naomi's FORK of solrmarc 2011-01-23
# Naomi Dushay 2013-08-14

# take an argument for the name of the log subdirectory
LOG_SUBDIR=$1

HOMEDIR=/home/blacklight
SOLRMARC_BASEDIR=$HOMEDIR/music-profiling

# create fresh dist files
ant -buildfile $SOLRMARC_BASEDIR/build.xml dist_site

# index the files
/home/blacklight/music-profiling/profiling/scripts/index_sirsi_0000_0099.sh $LOG_SUBDIR &
/home/blacklight/music-profiling/profiling/scripts/index_sirsi_0100_0199.sh $LOG_SUBDIR &
/home/blacklight/music-profiling/profiling/scripts/index_sirsi_0200_0299.sh $LOG_SUBDIR &
/home/blacklight/music-profiling/profiling/scripts/index_sirsi_0300_0399.sh $LOG_SUBDIR &
/home/blacklight/music-profiling/profiling/scripts/index_sirsi_0400_0499.sh $LOG_SUBDIR &
/home/blacklight/music-profiling/profiling/scripts/index_sirsi_0500_0599.sh $LOG_SUBDIR &
/home/blacklight/music-profiling/profiling/scripts/index_sirsi_0600_0699.sh $LOG_SUBDIR &
/home/blacklight/music-profiling/profiling/scripts/index_sirsi_0700_0799.sh $LOG_SUBDIR &
/home/blacklight/music-profiling/profiling/scripts/index_sirsi_0800_0899.sh $LOG_SUBDIR &
/home/blacklight/music-profiling/profiling/scripts/index_sirsi_0900_0999.sh $LOG_SUBDIR &
/home/blacklight/music-profiling/profiling/scripts/index_sirsi_1000_1049.sh $LOG_SUBDIR &
#curl http://localhost:8983/solr/update?commit=true

exit 0
