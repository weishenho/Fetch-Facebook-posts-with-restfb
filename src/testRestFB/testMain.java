package testRestFB;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Version;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.json.*;

public class testMain {

	static int postcount = 0;

	static String keyword = "universalstudiosentertainment";

	public static void getFacebookPosts(String url) throws FileNotFoundException, UnsupportedEncodingException {
		try {			
			String accessToken = ""; //Get token from Facebook Graph API Explorer https://developers.facebook.com/tools/explorer/
			FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_0);

			//JsonArray ja = new JsonArray();

			Page page = client.fetchObject(url, Page.class);
			System.out.println(page.getName());
			Connection<Post> pageFeed = client.fetchConnection(page.getId() + "/feed", Post.class);

			PrintWriter writer = new PrintWriter(keyword + ".json", "UTF-8");
			writer.println("[");
			// Getting posts:
			for (List<Post> feed : pageFeed) {
				for (Post post : feed) {

					if (post.getMessage() != null) {
						JsonObject jo = new JsonObject();
						jo.add("id", post.getId());
						jo.add("created_time", post.getCreatedTime().toString());
						jo.add("msg", post.getMessage());
						System.out.println(jo + ", ");
						// ja.put(jo);
						postcount++;
					}
				}
			}

			while (pageFeed.hasNext() && postcount < 100000) {
				pageFeed = client.fetchConnectionPage(pageFeed.getNextPageUrl(), Post.class);

				for (List<Post> feed : pageFeed) {
					for (Post post : feed) {

						if (post.getMessage() != null) {
							JsonObject jo = new JsonObject();
							jo.add("id", post.getId());
							jo.add("created_time", post.getCreatedTime().toString());
							jo.add("msg", post.getMessage());
							System.out.println(jo);
							writer.println(jo + ", ");
							// ja.put(jo);
							postcount++;
						}

					}
				}
			}
			writer.println("]");
			writer.close();
			System.out.println("post count: " + postcount);
		} catch (com.restfb.exception.FacebookOAuthException ex) {
			System.out.println("\n!!!!!!! Token Expired !!!!!!!!\n");
		}
	}

	public static void main(String[] args) {

		try {
			getFacebookPosts(keyword);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
