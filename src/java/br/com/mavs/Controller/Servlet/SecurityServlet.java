package br.com.mavs.Controller.Servlet;

import br.com.mavs.Controller.ControlUser;
import br.com.mavs.DAO.SysDao;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.StimeSegmento;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;

@WebServlet("*.sec")
public class SecurityServlet extends HttpServlet {

    private static final long serialVersionUID = 8071426090770097330L;
    private StimeSegmento sSegmento;
    private Usuario usuario;

    public SecurityServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession httpSession = request.getSession();
        String faceCode = request.getParameter("code");
        String state = request.getParameter("state");
        String accessToken = getFacebookAccessToken(faceCode);
        String sessionID = httpSession.getId();
        if (state.equals(sessionID) && accessToken != null) {
            try {
                this.usuario = getUser(accessToken, httpSession);
                if (this.validaCadastro()) {
                    response.sendRedirect(request.getContextPath() + "/Home.xhtml");
                } else {
                    response.sendRedirect(request.getContextPath() + "/frmAprovacao.xhtml");
                }
            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/facebookConnectError.xhtml");
                return;
            }
        } else {
            System.err.println("CSRF protection validation");
            response.sendRedirect(request.getContextPath() + "/facebookConnectError.xhtml");
        }
    }

    /**
     *
     * @return
     */
    public boolean validaCadastro() {
        String erro = "";
        SloginUsers user = new ControlUser().findUser(usuario);

        if (user == null) {
            erro = new ControlUser().addUserTmp(usuario);
            System.out.println(erro);
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * @param faceCode
     * @return
     */
    private String getFacebookAccessToken(String faceCode) {
        String token = null;
        if (faceCode != null && !"".equals(faceCode)) {
//            String appId = "690153531004979"; // Produção
            String appId = "588204657972342"; // Teste
            String redirectUrl = ProxyAuthenticator.s_url_redirect + "index.sec";
//            String faceAppSecret = "8ed65c5171aa011415c99cb180a6d245"; // Produção
            String faceAppSecret = "197949691e874eec77f827e2fb7420ad"; // Teste
            String newUrl = "https://graph.facebook.com/oauth/access_token?client_id="
                    + appId + "&redirect_uri=" + redirectUrl + "&client_secret="
                    + faceAppSecret + "&code=" + faceCode;
            //HttpClient httpclient = new DefaultHttpClient();
            try {
                /*HttpGet httpget = new HttpGet(newUrl);
                 ResponseHandler<String> responseHandler = new BasicResponseHandler();
                 String responseBody = httpclient.execute(httpget, responseHandler);*/
                URL url = new URL(newUrl);
                URLConnection con = url.openConnection();
                InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                encoding = encoding == null ? "UTF-8" : encoding;
                String responseBody = IOUtils.toString(in, encoding);
                token = responseBody.substring(responseBody.indexOf("access_token=") + 13, responseBody.indexOf("&expires="));
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } /*finally {
             httpclient.getConnectionManager().shutdown();
             }*/

        }
        return token;
    }

    /**
     *
     * @param accessToken
     * @param httpSession
     * @return
     */
    private boolean validaGrupoJogadores(String accessToken, HttpSession httpSession) {
        boolean boolean_pertence_grupo = false;
        FacebookClient publicOnlyFacebookClient = null;
        try {
            publicOnlyFacebookClient = new DefaultFacebookClient(accessToken);
            com.restfb.Connection<Group> gr = publicOnlyFacebookClient.fetchConnection("me/groups", Group.class);

            List<Group> g = gr.getData();

            for (int i = 0; i < g.size(); i++) {
                Group group_jogadores = g.get(i);
                if (group_jogadores.getName().equalsIgnoreCase("Paulinia Mavericks - Jogadores")) {
                    boolean_pertence_grupo = true;
                    sSegmento = new StimeSegmento(1);
                    break;
                }
            }
            if (!boolean_pertence_grupo) {
                for (int i = 0; i < g.size(); i++) {
                    Group group_jogadores = g.get(i);
                    if (group_jogadores.getName().equalsIgnoreCase("Mavs Girls")) {
                        boolean_pertence_grupo = true;
                        sSegmento = new StimeSegmento(3);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return boolean_pertence_grupo;
    }

    /**
     *
     * @param accessToken
     * @param httpSession
     * @return
     */
    private Usuario getUser(String accessToken,
            HttpSession httpSession) {
        Usuario user = null;
        if (accessToken != null && !"".equals(accessToken)) {
            FacebookClient publicOnlyFacebookClient = new DefaultFacebookClient(accessToken);
            user = publicOnlyFacebookClient.fetchObject("me", Usuario.class);

            user.setPictureUrl("https://graph.facebook.com/" + user.getId() + "/picture");

//            Connection<Album> myAlbums = publicOnlyFacebookClient.fetchConnection("me/albums", Album.class);
//
//            user.setAlbums(myAlbums.getData());
            user.setAcessToken(accessToken);
//            
//            for (Album album : myAlbums.getData()) {
//                System.out.println(album.getName());
//                Connection<Photo> photo = publicOnlyFacebookClient.fetchConnection(album.getId() + "/photos", Photo.class);
//
//                String profileImgUrl = photo.getData().get(0).getSource();
//
//            }

            //JsonObject photosConnection = publicOnlyFacebookClient.fetchObject("me/photos", JsonObject.class);
            //String firstPhotoUrl = photosConnection.getJsonArray("data").getJsonObject(0).getString("source");
            //System.out.println(firstPhotoUrl);
            if (user.getGender().equalsIgnoreCase("male") || user.getGender().equalsIgnoreCase("masculino")) {
                user.setsSegmento(new StimeSegmento(1));
            } else {
                user.setsSegmento(new StimeSegmento(3));
            }

            httpSession.setAttribute("FACEBOOK_USER", user);
            httpSession.setAttribute("logado", "N");

        } else {
            System.err.println("Token for facebook is null");
        }
        return user;
    }

}
