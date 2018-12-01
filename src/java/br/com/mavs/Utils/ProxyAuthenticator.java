package br.com.mavs.Utils;

import br.com.mavs.Modal.Usuario;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Album;
import com.restfb.types.Photo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class ProxyAuthenticator extends Authenticator {

    private String user, password;

    public static String s_url_redirect;

    public ProxyAuthenticator(String user, String password) {
        this.user = user;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password.toCharArray());
    }

    /**
     *
     */
    public static void retornUrlRedirect() {
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            File url_redirect = new File(realPath + "/url_redirect.txt");
            BufferedReader reader;
            try (FileReader fileReader = new FileReader(url_redirect)) {
                reader = new BufferedReader(fileReader);
                s_url_redirect = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Photo> findPicturesByAlbum(Album p_album, String accessToken) {
        FacebookClient publicOnlyFacebookClient = new DefaultFacebookClient(accessToken);
        Connection<Photo> photo = publicOnlyFacebookClient.fetchConnection(p_album.getId() + "/photos", Photo.class);
        return photo.getData();
    }

    /**
     *
     * @return
     */
    public static Object getFromSession(String variavel) {
        HttpSession session
                = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Object user_facebook = session.getAttribute(variavel); //"FACEBOOK_USER"
        return user_facebook;
    }

    /**
     *
     * @return
     */
    public static void setFromSession(String variavel, Object Value) {
        HttpSession session
                = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute(variavel, Value); //"FACEBOOK_USER"
    }

    /**
     *
     * @param destinatario
     * @param mensagem
     * @throws EmailException
     */
    public static void sendEmail(Usuario destinatario, String mensagem) throws EmailException {
        SimpleEmail email = new SimpleEmail();
        email.setSSLOnConnect(true);
        email.setSSL(true);
        email.setHostName("smtp.gmail.com");
        email.setSslSmtpPort("465");
        email.setAuthentication("sugestao.mavs@gmail.com", "sugestaomavs");
        email.setFrom(destinatario.getEmail());

        email.setDebug(true);

        email.setSubject("Sugestão de " + destinatario.getFirstName());
        email.setMsg(mensagem);
        email.addTo("contato@mavericksfootball.com.br");

        email.send();
    }

    /**
     *
     * @throws IOException
     */
    public static void logoff() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        fc.getExternalContext().redirect("index.xhtml?faces-redirect=true");
    }
}
