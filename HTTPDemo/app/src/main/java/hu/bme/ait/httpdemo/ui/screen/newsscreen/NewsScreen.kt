package hu.bme.ait.httpdemo.ui.screen.newsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.revenuecat.placeholder.PlaceholderDefaults
import com.revenuecat.placeholder.placeholder
import hu.bme.ait.httpdemo.data.news.Article
import hu.bme.ait.httpdemo.data.news.NewsResult

@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Button(onClick = {
            newsViewModel.getNews()
        }) {
            Text(text = "Refresh")
        }

        when (newsViewModel.newsUiState) {
            is NewsUiState.Init -> {}
            is NewsUiState.Loading -> ResultScreenPlaceholder()
            is NewsUiState.Success -> ResultScreen((newsViewModel.newsUiState as NewsUiState.Success).news)
            is NewsUiState.Error -> Text(text = "Error: ${(newsViewModel.newsUiState as NewsUiState.Error).errorMsg}")
        }
    }
}

@Composable
fun ResultScreenString(news: String) {
    Text(text = news)
}

@Composable
fun ResultScreen(newsResult: NewsResult) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(newsResult.articles!!) {
            NewsCard(it!!)
        }
    }
}

@Composable
fun ResultScreenPlaceholder() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(5) {
            NewsCard(null, isLoading = true)
        }
    }
}

@Composable
fun NewsCard (
    article: Article? = null,
    isLoading: Boolean = false
) {
    val uriHandler = LocalUriHandler.current

    val annotatedString = buildAnnotatedString {
        //append(article.url)
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, color = Color.Blue)) {
            append("Link")
            addStringAnnotation(
                tag = "URL",
                annotation = if (!isLoading) article?.url!! else "",
                start = if (!isLoading) length - article?.url!!.length else 0,
                end = length
            )
        }
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = article?.author ?: "Loading text",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.placeholder(
                    enabled = isLoading,
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderDefaults.shimmer
                )
            )
            Text(
                text = article?.publishedAt ?: "Loading text",
                fontSize = 12.sp,
                modifier = Modifier.placeholder(
                    enabled = isLoading,
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderDefaults.shimmer
                )
            )
            Text(
                text = article?.title ?: "Loading text",
                modifier = Modifier.placeholder(
                    enabled = isLoading,
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderDefaults.shimmer
                )
            )
            if (article?.urlToImage != "") {
                AsyncImage(
                    model = article?.urlToImage,
                    modifier = Modifier.size(200.dp, 100.dp).placeholder(
                        enabled = isLoading,
                        shape = CircleShape,
                        highlight = PlaceholderDefaults.shimmer
                    ),
                    contentDescription = "selected image"
                )
            }
            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                        .firstOrNull()?.let { annotation ->
                            uriHandler.openUri(annotation.item)
                        }
                },
                modifier = Modifier.placeholder(
                    enabled = isLoading,
                    shape = CircleShape,
                    highlight = PlaceholderDefaults.shimmer
                )
            )
        }
    }
}