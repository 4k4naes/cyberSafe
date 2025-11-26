package com.example.cybersafeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GuideScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            "Poradnik Cyberbezpieczeństwa",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(16.dp))

        GuideSection(
            title = "1. Silne i unikalne hasła",
            description =
                "• Używaj minimum 12 znaków.\n" +
                        "• Mieszaj małe i duże litery, cyfry i symbole.\n" +
                        "• Nie używaj jednego hasła do wielu kont.\n" +
                        "• Rozważ menedżer haseł."
        )

        GuideSection(
            title = "2. Włącz uwierzytelnianie dwuskładnikowe (2FA)",
            description =
                "• Dodaje dodatkową warstwę ochrony.\n" +
                        "• Najbezpieczniejsze formy to aplikacje (Google Authenticator, Authy) lub klucze U2F.\n" +
                        "• Unikaj SMS–ów jako jedynego zabezpieczenia."
        )

        GuideSection(
            title = "3. Uważaj na phishing",
            description =
                "• Nie klikaj w podejrzane linki z e-maila/SMS.\n" +
                        "• Sprawdzaj nadawcę, domenę oraz treść komunikatu.\n" +
                        "• Banki nigdy nie proszą o podanie hasła przez maila."
        )

        GuideSection(
            title = "4. Aktualizuj system i aplikacje",
            description =
                "• Aktualizacje łatają luki bezpieczeństwa.\n" +
                        "• Nie odkładaj ich na później.\n" +
                        "• Regularnie restartuj urządzenia."
        )

        GuideSection(
            title = "5. Korzystaj z VPN w publicznych Wi‑Fi",
            description =
                "• Publiczne sieci są narażone na podsłuch.\n" +
                        "• VPN szyfruje połączenie i ukrywa Twój IP.\n" +
                        "• Unikaj logowania do banku bez VPN."
        )

        GuideSection(
            title = "6. Pilnuj swoich danych osobowych",
            description =
                "• Nie podawaj PESEL, numeru dowodu, adresu bez potrzeby.\n" +
                        "• Sprawdzaj, komu udostępniasz zdjęcia i dokumenty.\n" +
                        "• Uważaj na fałszywe konkursy i ankiety."
        )

        GuideSection(
            title = "7. Bezpieczne korzystanie z mediów społecznościowych",
            description =
                "• Ustaw profile jako prywatne.\n" +
                        "• Nie udostępniaj lokalizacji w czasie rzeczywistym.\n" +
                        "• Chroń swoje zdjęcia przed publicznym dostępem."
        )

        GuideSection(
            title = "8. Rób kopie zapasowe",
            description =
                "• Zabezpiecza to dane przed ransomware.\n" +
                        "• Wykorzystuj chmurę lub zewnętrzne dyski.\n" +
                        "• Kopie rób przynajmniej raz w miesiącu."
        )

        Spacer(Modifier.height(40.dp))
    }
}

@Composable
fun GuideSection(title: String, description: String) {
    Text(title, style = MaterialTheme.typography.titleMedium)
    Spacer(Modifier.height(6.dp))
    Text(description, style = MaterialTheme.typography.bodyMedium)
    Spacer(Modifier.height(16.dp))
}
