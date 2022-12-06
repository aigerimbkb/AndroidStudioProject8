package kz.talipovsn.rates;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// СОЗДАТЕЛЬ КОТИРОВОК ВАЛЮТ
public class RatesReader {

    private static final String BASE_URL = "https://github.com/tsnsoft/Orix_2022/commits/master"; // Адрес с котировками

    // Парсинг котировок из формата html web-страницы банка, при ошибке доступа возвращаем null
    public static String getRatesData() {
        StringBuilder data = new StringBuilder();
        try {
            System.out.println("#");
            System.out.println(BASE_URL);

            Document doc = Jsoup.connect(BASE_URL).timeout(15000).get();
            System.out.println("!");
            data.append("Обновления: \n");
            Elements box = doc.select("li.Box-row");

            Elements message = doc.select("p.mb-1");

            Elements login = doc.select("a.commit-author");

            Elements date = doc.select("relative-time.no-wrap");

            for (int i = 0; i < box.size(); i++) {
                Element h2 = message.get(i);
                Element a = login.get(i);
                Element d = date.get(i);

                data.append("Сообщение: " + h2.text() + "\n");
                data.append("Логин: " + a.text() + "\n");
                data.append("Дата: " + d.text() + "\n\n");
            }
        } catch (Exception e) {
            System.out.println(e.toString());

            return null; // При ошибке доступа возвращаем null
        }
        return data.toString().trim(); // Возвращаем результат
    }

}