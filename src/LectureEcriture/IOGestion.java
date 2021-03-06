package LectureEcriture;

import Donnees.Fabrique;
import Donnees.ObjetModifiable;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IOGestion {
    static List<Object> lecture(Fabrique f, String fichier, XMLStreamReader fichierConsultable)
            throws XMLStreamException, ParseException, FileNotFoundException {
        XMLLecteur lecteur;
        if (fichierConsultable == null) {
            lecteur = new XMLLecteur(fichier);
        } else {
            lecteur = new XMLLecteur(fichierConsultable);
        }
        ArrayList<String> donnees = lecteur.lectureDonnee();
        XMLStreamReader fichierLecteur = lecteur.getFichierConsultable();
        return Arrays.asList(f.generer(donnees), fichierLecteur);
    }

    static void ecriture(ArrayList<ArrayList<String>> listeDecoupe) throws XMLStreamException, FileNotFoundException {
        XMLEcriture ecrivain;
        ecrivain = new XMLEcriture("decoupe.xml");
        ecrivain.ecritureFichier(listeDecoupe);
    }
}
