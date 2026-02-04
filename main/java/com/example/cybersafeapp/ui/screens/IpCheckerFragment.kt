package com.example.cybersafeapp.ui.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cybersafeapp.R
import java.net.InetAddress
import java.util.regex.Pattern

// na 90% nie bedzie pobieranie ip dzialac na telefonie ale tu dziala

class IpCheckerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_ip_checker, container, false)

        val ipInput = view.findViewById<EditText>(R.id.ipInput)
        val resultText = view.findViewById<TextView>(R.id.resultText)
        val checkButton = view.findViewById<Button>(R.id.checkButton)
        val myIpButton = view.findViewById<Button>(R.id.myIpButton)

        checkButton.setOnClickListener {
            val ip = ipInput.text.toString()
            resultText.text = analyzeIp(ip)
        }

        myIpButton.setOnClickListener {
            val myIp = getLocalIp()
            resultText.text = "Twoje IP:\n$myIp\n\n${analyzeIp(myIp)}"
        }

        return view
    }

    private fun analyzeIp(ip: String): String {
        if (!isValidIp(ip)) {
            return "Nieprawidłowy adres IP"
        }

        val isPrivate = isPrivateIp(ip)

        return buildString {
            append("Adres IP: $ip\n")
            append("Typ: ${if (isPrivate) "Prywatne (LAN)" else "Publiczne"}\n")
            append(getNetworkInfo())
        }
    }

    private fun isValidIp(ip: String): Boolean {
        val ipPattern = Pattern.compile(
            "^((25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.)){3}(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)$"
        )
        return ipPattern.matcher(ip).matches()
    }

    private fun isPrivateIp(ip: String): Boolean {
        val address = InetAddress.getByName(ip)
        return address.isSiteLocalAddress
    }

    private fun getLocalIp(): String {
        val cm = requireContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = cm.activeNetwork ?: return "Brak połączenia"
        val caps = cm.getNetworkCapabilities(network) ?: return "Brak połączenia"

        if (!caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return "Nie jesteś połączony z Wi-Fi"
        }

        val wifiManager =
            requireContext().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val ip = wifiManager.connectionInfo.ipAddress

        if (ip == 0) return "Nie udało się pobrać IP"

        return String.format(
            "%d.%d.%d.%d",
            ip and 0xff,
            ip shr 8 and 0xff,
            ip shr 16 and 0xff,
            ip shr 24 and 0xff
        )
    }


    private fun getNetworkInfo(): String {
        val cm =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return "Brak połączenia z siecią"
        val capabilities = cm.getNetworkCapabilities(network) ?: return ""

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->
                "Połączenie: Wi-Fi\n" +
                        "Prędkość linku: ${getWifiSpeed()} Mbps"

            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->
                "Połączenie: Sieć komórkowa"

            else -> "Nieznany typ sieci"
        }
    }

    private fun getWifiSpeed(): Int {
        val wifiManager =
            requireContext().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.connectionInfo.linkSpeed
    }
}
