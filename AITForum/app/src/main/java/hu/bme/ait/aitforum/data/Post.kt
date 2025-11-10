package hu.bme.ait.aitforum.data

data class Post(
    var uid: String = "", // user id
    var author: String = "",
    var postDate: String = "",
    var postTitle: String = "",
    var postBody: String = ""
)

data class PostWithId(
    var postId: String = "",
    var post: Post
)