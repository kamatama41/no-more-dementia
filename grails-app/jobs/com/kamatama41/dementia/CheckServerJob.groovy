package com.kamatama41.dementia

import org.slf4j.LoggerFactory



class CheckServerJob {
    private static def logger = LoggerFactory.getLogger(CheckServerJob)

    static def cronExpression = '0 0 1/2 * * ?'
//	static def cronExpression = '0/30 * * * * ?'
    def url = 'http://nomore-dementia.kamatama.cloudbees.net/'

    static triggers = {
        cron startDelay: 30000L, cronExpression: cronExpression
    }

    def execute() {
        accessWebServer()
    }

    private def accessWebServer() {
        def http = (HttpURLConnection) new URL(url).openConnection()
        http.setRequestMethod("GET")
        http.connect()
        def responseCode = http.responseCode

        logger.debug("URL: ${url}, ResponseCode:${responseCode}")

        if(responseCode != 200) {
            logger.warn("Unexpected response code ${responseCode}")
        }
    }
}
