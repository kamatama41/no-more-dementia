package com.kamatama41.dementia

import org.slf4j.LoggerFactory



class TwitterFollowingJob {
    private static def logger = LoggerFactory.getLogger(TwitterFollowingJob)
    def twitterService
    static triggers = {
        simple repeatInterval: 5 * 60 *  1000l // execute job once in 5 minutes
    }

    def execute() {
        def nowFollowings = twitterService.getFollowingIDs()
        logger.debug("nowFollowings ${nowFollowings}")
        def nowFollowers = twitterService.getFollowerIDs()
        logger.debug("nowFollowers ${nowFollowers}")

        def follows = nowFollowers - nowFollowings
        follows.each { id ->
            twitterService.follow(id)
            logger.info("User({$id}) follow.")
        }
        def unfollows = nowFollowings - nowFollowers
        unfollows.each { id ->
            twitterService.unfollow(id)
            logger.info("User({$id}) unfollow.")
        }
    }
}
