# AlphaBankTest

После запуска сервиса, ввести в браузере адрес http://localhost:8080/swagger-ui/index.html

Появится три end-point'а - welcome, compareWithYesterdayUSDRate, compareUSDRateByDate

Для вызова end-point'а, необходимо открыть соответствующую вкладку, нажать кнопку "Try it out", ввести required параметры и нажать кнопку "execute".

1. Первый end-point тестовый, выводит сообщение Welcome!

2. compareWithYesterdayUSDRate отображает GIF анимацию по условию задачи, после ввода параметра currency

3. compareUSDRateByDate отображает GIF анимацию по условию задачи, после ввода параметра "currency" и интересующей даты в формате дд.ММ.ГГГГ (например, 20.05.2022)

Также, сервисом можно пользоваться без использования Swagger, путем ввода в адресную строку:

http://localhost:8080/indicator/welcome

http://localhost:8080/indicator/compareWithYesterdayUSDRate?currency=rub

http://localhost:8080/indicator/compareUSDRateByDate?date=20.05.2022&currency=rub
