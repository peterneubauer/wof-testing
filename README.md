# A testing project for Whos-on-first

This repo is testing the validity of shapes from https://github.com/whosonfirst-data/whosonfirst-data

The libraries used are the ones that Elasticsearch uses to parse shapes, hopefully we by this can make the shapes more indexing-friendly.

The data is retrieved via URls in the `region.txt` file for all regions in WOF (to start with).


# Running the test

    .gradlew test
    open build/reports/tests/test/index.html