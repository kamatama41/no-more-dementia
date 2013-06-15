package com.kamatama41.dementia

import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken

class TwitterService {

    private final Twitter twitter

    TwitterService(config) {
        def singleton = TwitterFactory.singleton
        singleton.setOAuthConsumer(config.consumerKey, config.consumerSecret)
        singleton.setOAuthAccessToken(new AccessToken(config.token, config.tokenSecret))
        twitter = singleton
    }

    def tweet(String message) {
        twitter.updateStatus(message)
    }

    def getFollowingIDs() {
        def friendIds = twitter.getFriendsIDs(-1)
        def result = friendIds.IDs
        while (friendIds.hasNext()) {
            friendIds = twitter.getFriendsIDs(friendIds.nextCursor)
            result << friendIds.IDs
        }
        return result.toList()
    }

    def getFollowerIDs() {
        def followerIds = twitter.getFollowersIDs(-1)
        def result = followerIds.IDs
        while(followerIds.hasNext()) {
            followerIds = twitter.getFollowersIDs(followerIds.nextCursor)
            result << followerIds.IDs
        }
        return result.toList()
    }

    def getFollowers() {
        def followers = twitter.getFollowersList('nomoredementia', -1)
        def result = followers.toList()
        while(followers.hasNext()) {
            followers = twitter.getFollowersList('nomoredementia', followers.nextCursor)
            result << followers.toList()
        }
        return result
    }

    def follow(userId) {
        twitter.createFriendship(userId)
    }

    def unfollow(userId) {
        twitter.destroyFriendship(userId)
    }
}
