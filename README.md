# ExcursieReactApp
Aplicatie de gestionare a excursiilor folosind Spring, servicii REST si React

Aplicatia ajuta un client sa poata rezerva bilete pentru niste excursii ale unei autogari. In ClientFX avem start-ul pentru GUI-ul in FXML
al aplicatiei. In server este StartServer. Am adaugat servicii REST (cu start in ServiciiRest/src/main/java/start/StartRestServices) si 
apoi am creat o interfata React pentru adaugare si stergere excursii, care poate fi pornita din react-client.

Functionalitati:

FXML:
- vizualizare lista excursii
- cautare excursii dupa destinatie si interval orar
- rezervare excursie: clientul isi introduce datele si numarul de locuri dorite si sistemul creaza un bilet si actualizeaza excursia
- logout

REST:
- efectuare operatii put/delete/get

REACT:
- vizualizare excursii
- adaugare excursie
- stergere excursie
