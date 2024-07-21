package ren.pj.notification.mapper

internal object CurrencyMapper {

    private val currencySymbolToCode: Map<String, String> = mapOf(
        "€" to "EUR",
        "$" to "USD",
        "£" to "GBP",
        "¥" to "JPY",
        "₽" to "RUB",
        "₩" to "KRW",
        "₹" to "INR",
        "₪" to "ILS",
        "₫" to "VND",
        "฿" to "THB",
        "₴" to "UAH",
        "₦" to "NGN",
        "₣" to "CHF",
        "₨" to "PKR",
        "₮" to "MNT"
    )

    fun mapSymbolToCode(symbol: String) = currencySymbolToCode[symbol]
}