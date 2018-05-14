# Fetch-Facebook-posts-with-restfb

Retrieve posts from a facebook page e.g. https://www.facebook.com/universalstudiosentertainment/.

Store the posts in JSON format, suitable for solr to consume.

Tried and tested on eclipse IDE.

## Usage
Add restfb library to build path
```
Right click project and select properties > Java Build path > Add JARs > restfb-*.jar > apply and close
```

line 20 of testMain.java, assign keyword variable the desired facebook page.
```
static String keyword = "universalstudiosentertainment";
```

line 24 of testMain.java, enter a working accesstoken (able to get it in https://developers.facebook.com/tools/explorer/)
```
String accessToken = "";
```
