package com.kamatama41.dementia

import org.slf4j.LoggerFactory



class TwitterFollowingJob {
    private static def logger = LoggerFactory.getLogger(TwitterFollowingJob)
    def twitterService
    static triggers = {
        simple startDelay: 30 * 1000l, repeatInterval: 5 * 60 *  1000l // execute job once in 5 minutes
    }

    def execute() {
        def nowFollowings = twitterService.getFollowingIDs()
        logger.debug("nowFollowings ${nowFollowings}")
        def nowFollowers = twitterService.getFollowerIDs()
        logger.debug("nowFollowers ${nowFollowers}")

        def follows = nowFollowers - nowFollowings
        follows.each { id ->
            def user = twitterService.follow(id)
            // ツイートする
            twitterService.tweet("@${user.screenName} さん、フォローありがとう！今日の予定を復唱してみましょう♪")
            logger.info("User({$id}) follow.")
        }
        def unfollows = nowFollowings - nowFollowers
        unfollows.each { id ->
            twitterService.unfollow(id)
            logger.info("User({$id}) unfollow.")
        }
    }
}
