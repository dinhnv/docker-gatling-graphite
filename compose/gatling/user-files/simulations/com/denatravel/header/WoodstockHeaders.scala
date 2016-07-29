package snoopydoo

object WoodstockHeaders {
  val httpHeader1 = Map("X-Request-ID" -> "${requestId}")
  val httpHeader2 = Map(
                      "Content-Type" -> "application/json"
                       )
  val httpHeaderWithPublicKey = Map(
                      "Content-Type" -> "application/json",
                      "X-User-ID" -> "${xUserId}",
                      "X-Transaction-ID" -> "${processInfoKey}"
                       )

  val httpHeaderWithCSRF = Map(
                      "Content-Type" -> "application/json",
                      "X-CSRF-TOKEN" -> "${tokenCSRF}",
                      "X-User-ID" -> "${xUserId}",
                      "X-Transaction-ID" -> "${processInfoKey}"
                       )

  val httpHeaderWithCSRFWithRqId = Map(
                      "Content-Type" -> "application/json",
                      "X-CSRF-TOKEN" -> "${tokenCSRF}",
                      "X-User-ID" -> "${xUserId}",
                      "X-Transaction-ID" -> "${processInfoKey}",
                      "X-Request-ID" -> "${xRequestId}"
                       )


  val apiHeaders = """{
          "uid": "0ABAA87F-079C-3915-3CF2-65D2B6904A89",
          "languageCode": "en",
          "countryCode": "AU",
          "currencyCode": "AUD",
          "inflow": "FRONT",
          "deviceType": "pc",
          "userAgent": "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4",
          "userIp": "127.0.0.1"
      }
  """

  val apiHeadersWithCSRF = """{
          "uid": "0ABAA87F-079C-3915-3CF2-65D2B6904A89",
          "languageCode": "en",
          "countryCode": "US",
          "currencyCode": "USD",
          "inflow": "FRONT",
          "deviceType": "pc",
          "userAgent": "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4",
          "userIp": "127.0.0.1"
      }
  """



}

