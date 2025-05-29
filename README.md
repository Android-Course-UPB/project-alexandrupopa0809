# Convertor Valutar - Aplicație Android

Această aplicație Android permite utilizatorilor să convertească sume dintr-o monedă în alta folosind un API valutar. Aplicația utilizează Jetpack Compose pentru interfața grafică și arhitectura MVVM (Model-View-ViewModel) pentru gestionarea datelor și stării.

## Funcționalități

- Conversie valutară în timp real
- Selectarea monedei de intrare și ieșire
- Suport pentru mai multe limbi (română, engleză, germană)
- Posibilitatea de a selecta culoarea butoanelor
- Dropdown-uri căutabile pentru monede
- Istoric al conversiilor salvate local
- Interfață modernă și intuitivă construită cu Jetpack Compose

## Tehnologii utilizate

- **Jetpack Compose** – UI declarativ modern
- **Kotlin** – limbajul principal
- **MVVM** – Model-View-ViewModel pentru arhitectură clară
- **Room** – salvarea locală a istoricului
- **Retrofit** – consum REST API pentru cursuri valutare
- **Multilingv** – suport pentru RO, EN, DE
- **Gradle** – build & dependency management

## Persistența datelor cu Room – Istoric Conversii Valutare

Această componentă gestionează salvarea și accesarea istoricului conversiilor valutare utilizând biblioteca Room.

### ConversionEntity

Entitatea care reprezintă o conversie valutară în baza de date.

- `id`: identificator unic generat automat.
- `fromCurrency`: codul monedei de origine.
- `toCurrency`: codul monedei de destinație.
- `rate`: cursul de schimb utilizat.
- `amount`: suma convertită.
- `timestamp`: momentul conversiei (reprezentat în milisecunde).

### ConversionDao

Interfața DAO pentru accesul la datele conversiilor.

- `insert(conversion: ConversionEntity)`: inserează sau actualizează o conversie în baza de date.
- `getLast10()`: returnează ultimele 10 conversii, ordonate descrescător după timestamp.
- `clearAll()`: șterge toate înregistrările din istoricul conversiilor.

### AppDatabase

Clasa abstractă care extinde RoomDatabase și oferă acces la DAO-ul de conversii.

- Metoda `conversionDao()` returnează instanța DAO pentru conversii.

## Integrarea Retrofit – Obținerea cursurilor valutare

Această componentă se ocupă de comunicarea cu API-ul extern pentru a prelua cursurile valutare actualizate folosind biblioteca Retrofit.

### ExchangeRatesResponse

Modelul de date care reprezintă răspunsul primit de la API:

- `date`: data la care se referă cursurile.
- `base`: moneda de bază pentru conversii.
- `rates`: un map cu perechi valută–curs (cod valută -> valoare curs).

### CurrencyApiService

Interfața care definește endpoint-urile API-ului pentru Retrofit:

- `getLatestRates(apiKey, base)`: solicită ultimele cursuri valutare.
    - Parametri:
        - `apiKey`: cheia de acces la API.
        - `base`: moneda de referință (implicit "USD").
    - Returnează un obiect `ExchangeRatesResponse`.

### RetrofitInstance

Obiect singleton care configurează Retrofit pentru accesul la API:

- `BASE_URL`: URL-ul de bază al serviciului de cursuri valutare.
- `api`: instanța `CurrencyApiService` creată cu Retrofit, configurată cu converter Gson.

## ViewModels – Gestionarea logicii UI și a datelor în aplicație

Această componentă gestionează starea UI și operațiile de business logic pentru conversii valutare și setările aplicației.

### MainViewModel

Clasa `MainViewModel` extinde `AndroidViewModel` și se ocupă cu:

- Gestionarea stării pentru conversia valutară (monede, sumă, rezultat).
- Comunicarea cu API-ul pentru obținerea cursurilor valutare.
- Persistența conversiilor în baza de date locală folosind Room.
- Încărcarea istoricului ultimelelor 10 conversii.
- Tratarea erorilor și afișarea stării de încărcare.

#### Funcții principale

- `fetchRates(apiKey)`: încarcă cursurile valutare folosind Retrofit, actualizează starea `rates` și gestionează încărcarea/erorile.
- `convert()`: realizează conversia folosind rata curentă, actualizează `result` și salvează conversia în baza locală.
- `getConversionRate(from, to)`: calculează rata de conversie între două monede folosind cursurile obținute.
- `loadHistoryFromDb(language)`: preia ultimele 10 conversii din baza locală și actualizează lista `history` cu texte formatate în funcție de limbă.

#### Persistență

- Baza de date Room este creată în ViewModel pentru acces rapid.
- Conversiile sunt inserate asincron cu corutine.

---

### SettingsViewModel

Clasa `SettingsViewModel` gestionează stările legate de configurarea aplicației:

## Screens – Interfețele utilizator în Jetpack Compose

Această componentă definește ecranele principale ale aplicației folosind Compose, gestionând UI-ul pentru conversii, setări și istoric.

---

### MainScreen

Ecranul principal pentru conversia valutară.

#### Funcționalități

- Încarcă cursurile valutare la lansare (dacă nu sunt deja încărcate).
- Permite selectarea monedelor „from” și „to” prin dropdown-uri sau un dropdown căutabil.
- Oferă un buton pentru a comuta între afișarea monedelor populare și a tuturor monedelor.
- Permite introducerea sumei și conversia acesteia.
- Afișează rezultatul conversiei.
- Navighează către ecranul de istoric și setări.
- Afișează încărcarea și mesajele de eroare.

---

### SettingsScreen

Ecran pentru configurarea aplicației.

#### Funcționalități

- Selectarea limbii aplicației prin radio buttons.
- Alegerea culorii butoanelor dintr-o paletă prestabilită.
- Buton de întoarcere la ecranul anterior.
- Actualizarea dinamică a UI-ului în funcție de setările curente.

---

### HistoryScreen

Ecran pentru vizualizarea istoricului conversiilor.

#### Funcționalități

- Încarcă ultimele 10 conversii din baza de date la lansare.
- Afișează lista conversiilor în format text.
- Buton de întoarcere la ecranul anterior.

---
