package com.kamatama41.dementia

import org.slf4j.LoggerFactory

import java.util.concurrent.TimeUnit



class TwitterMentionJob {
    private static def logger = LoggerFactory.getLogger(TwitterFollowingJob)
    def twitterService
    // Server[13:00] -> Japan[21:00]
    static def cronExpression = '0 0 13 * * ?'

    static triggers = {
        cron startDelay: 30000L, cronExpression: cronExpression
//        simple repeatInterval: 5 * 60 *  1000l // execute job once in 5 minutes
    }

    def execute() {
        def followers = twitterService.getFollowers()
        followers.each { user ->
            def message = "@${user.screenName} さん、${choiceMessage()}"
            twitterService.tweet(message)
            logger.info("tweet. [Message]:${message}")
            // 5秒あけておく
            TimeUnit.SECONDS.sleep(5)
        }
    }

    private def messages = [
            '今日の朝ご飯は何でしたか？',
            '今日の昼ご飯は何でしたか？',
            '今日の晩ご飯は何でしたか？',
            '今日の天気は何でしたか？',
            '今日は誰とお話しましたか？',
            '今日はどこにお出かけしましたか？',
    ]
    private def random = new Random()
    private def choiceMessage() {
        messages[random.nextInt(messages.size()-1)]
    }
}
