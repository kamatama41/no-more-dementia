import com.kamatama41.dementia.TwitterService

// Place your Spring DSL code here
beans = {
    twitterService(TwitterService, grailsApplication.config.twitter)
}