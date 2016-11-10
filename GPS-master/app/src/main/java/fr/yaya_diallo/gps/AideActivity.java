package fr.yaya_diallo.gps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class AideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide);

        TextView textView = (TextView) findViewById(R.id.aideview);

        String html = "<html><body>" +
                "<strong>Comment ça marche ?</strong><br/><p>Kiraah est une application qui vous permet d'aller d'un endroit &agrave; un autre &agrave; l'aide de vos coordonn&eacute;es GPS.</p>" +
                "<p>L'application comprend quatre parties :<br/><br/>" +
                "1 - <u>Ma position</u> : Cette partie vous permet d'afficher vos coordonn&eacute;es GPS (Vous devez pour cela activer votre GPS)<br/>" +
                "2 - <u>Envoyer mes coordonn&eacute;es</u> : cette seconde partie vous permet d'envoyer vos coordonn&eacute;es GPS &agrave; une personne &agrave; partir de SMS (prix d'un sms - v&eacute;rifiez le tarif avec votre op&eacute;rateur)<br/>" +
                "3 - <u>Aller &agrave;...</u> : Vous affiche l'ensemble des coordonn&eacute;es GPS que vous avez re&ccedil;u et vous permet d'aller &agrave; une adresse donn&eacute;e.<br/>" +
                "4 - <u>Les lieux importants</u> : Cette derni&egrave;re partie vous permet de rejoindre rapidement les lieux les plus importants et les plus tendances en Guin&eacute;e.<br/>" +
                "</p><br/>" +
                "<strong>Pourquoi utiliser Kiraah ?</strong><br/>" +
                "<p>Le syst&egrave;me de num&eacute;rotation des rues n'&eacute;tant pas d&eacute;velopp&eacute; dans tous les pays et tous les coins du monde, " +
                "Kiraah vous permettra de circuler et de retrouver vos amis et votre chemin rapidement. L'application est gratuite, sans pub et simple &agrave; utiliser." +
                "</p><br/>" +
                "<strong>Nous suivre et nous contacter.</strong><br/>" +
                "<p>Vous avez des questions, des suggestions, des remarques ?</p>" +
                "<p>Vous pouvez nous contacter :</p>" +
                "<p>1 - Par email &agrave; l'adresse <a href=\"mailto:kiraahgps@gmail.com\">kiraahgps@gmail.com</a></p>" +
                "<p>2 - Par Facebook en devenant fan de notre page <a href=\"https://www.facebook.com/kiraahgps\">kiraahgps</a> pour retrouver les explications et les futures &eacute;volutions de l'application.</p>" +
                "<p>3 - Sur Twitter en s'abonnant au compte <a href=\"https://twitter.com/kiraahgps\">@kiraahgps</a></p>" +
                "<p>4 - Par t&eacute;l&eacute;phone &agrave; ces num&eacute;ros-l&agrave; : (+224) 628.44.76.83 ou au (+33) 06.43.40.82.54</p>" +
                "</p></body></html>";
        textView.setText(Html.fromHtml(html));
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.9));
    }
}
