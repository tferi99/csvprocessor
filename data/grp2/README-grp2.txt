GRP2

- 2-es csoportokat keresek, ahol
- a korábbi 'Journal Date' sorban az 'USD Amount' negatív (usdAmount1)
- a későbbi 'Journal Date' sorban az 'USD Amount' pozitív (usdAmount2)
- kimenet csoportonként 1 sor lesz:
	- Open Item Key
- Cost Centre
- Account
- Situation
- USD Amount: (usdAmount1 + usdAmount2) * -1
- Name

- a többi sor megy a REST-be

