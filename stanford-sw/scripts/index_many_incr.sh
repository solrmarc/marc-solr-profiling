#! /bin/bash

# revise this so there is only one commit at the end of all.
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120317
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120318
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120319
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120320
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120321
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120322
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120323
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120324
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120325
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120326
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120327
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120328
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120329
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120330
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120331
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120401
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120402
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120403
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120404
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120405
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120406
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120407
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120408
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120409
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120410
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120411
/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncr.sh 120412
#/home/blacklight/solrmarc-sw/stanford-sw/scripts/pullThenIndexSirsiIncrOpt.sh 120217

# include latest course reserves data
JRUBY_OPTS="--1.9"
export JRUBY_OPTS
/usr/local/rvm/wrappers/jruby-1.6.7\@crez-sw-ingest /home/blacklight/crez-sw-ingest/bin/pull_and_index_latest -s prod
# source /home/blacklight/crez-sw-ingest/bin/index_latest.sh -s prod


echo "!!! RUN SEARCHWORKS TESTS before putting index into production !!!"
echo "!!! CHGRP before putting index into production !!!"
