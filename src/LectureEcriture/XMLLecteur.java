package LectureEcriture;

import Donnees.ObjetModifiable;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class XMLLecteur implements IOGestion {

    private final XMLStreamReader fichierConsultable;
    private final List<ObjetModifiable> listeDonnees = new ArrayList<>();

    public XMLLecteur(String fichierNom) throws FileNotFoundException, XMLStreamException {
        FileInputStream fichier = new FileInputStream(fichierNom);
        XMLInputFactory fichierParser = XMLInputFactory.newInstance();
        fichierConsultable = fichierParser.createXMLStreamReader(fichier);
    }

    public void LectureDonnee() throws XMLStreamException, ParseException {
        int evenement;
        while (fichierConsultable.hasNext()) {
            evenement = fichierConsultable.next();
            if (evenement == XMLEvent.START_ELEMENT &&
                    fichierConsultable.getLocalName().equals("client")) {
                creerClient();
            }
        }
    }

    private void creerClient() throws XMLStreamException, ParseException {
        String idClientTexte = fichierConsultable.getAttributeValue(null, "id");
        int idClient = Integer.parseInt(idClientTexte);

        listeDonnees.add(client);
        creerPlanche(idClient);
    }

    private void creerPlanche(int idClient) throws XMLStreamException, ParseException {
        while (fichierConsultable.hasNext()) {
            int evenement = fichierConsultable.next();
            if (evenement == XMLEvent.END_ELEMENT && fichierConsultable.getLocalName().equals("client")) {
                break;
            }
            if (evenement == XMLEvent.START_ELEMENT &&
                    fichierConsultable.getLocalName().equals(("planche"))) {

                String idPlancheTexte = fichierConsultable.getAttributeValue(null,
                        "id");
                String nombreTexte = fichierConsultable.getAttributeValue(null,
                        "nombre");
                String prixTexte = fichierConsultable.getAttributeValue(null,
                        "prix");
                String dateTexte = fichierConsultable.getAttributeValue(null,"date");

                int idPlanche = Integer.parseInt(idPlancheTexte);
                int nombre = Integer.parseInt(nombreTexte);
                double prixNombre = Double.parseDouble(prixTexte);
                Prix prix = new Prix(prixNombre);
                Date dateFormat = new SimpleDateFormat("dd.MM.yy").parse(dateTexte);
                Date date = new Date(dateFormat);

                Planche planche = new Planche(idPlanche,nombre,prix,date,idClient);
                listeDonnees.add(planche);
            }
        }
    }

    List<ObjetModifiable> getListeDonnees() {
        return listeDonnees;
    }

    @Override
    public ObjetModifiable creationObjetModifiable() {

    }
}
