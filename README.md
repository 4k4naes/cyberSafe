# CyberSafeApp

Prosta aplikacja Android do podstawowych narzędzi związanych z cyberbezpieczeństwem.

## Funkcje
- Sprawdzanie adresu IP
- Generator haseł
- Sprawdzanie wycieków email
- Aktualności cyberbezpieczeństwa
- Poradnik (guide)
- Tryb ciemny (ustawienia)

## Struktura
Aplikacja oparta na jednej aktywności (`MainActivity`) i wielu fragmentach:
- `IpCheckerFragment`
- `CyberNewsFragment`
- `GuideFragment`
- `EmailLeakFragment`
- `PasswordGeneratorFragment`
- `SettingsFragment`

Nawigacja realizowana przez `BottomNavigationView`.

## Technologie
- Kotlin
- AndroidX
- Material Components

## Uruchomienie
1. Otwórz projekt w Android Studio
2. Zbuduj projekt
3. Uruchom na emulatorze lub urządzeniu
