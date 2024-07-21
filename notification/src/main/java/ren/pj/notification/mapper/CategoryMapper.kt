package ren.pj.notification.mapper

import ren.pj.core.data.Category

internal object CategoryMapper {

    private val categoryToKeywords: Map<Category, List<String>> = mapOf(
        Category.MEDICINE to listOf(
            "pharmacy",
            "аптека",
            "medication",
            "лекарства",
            "drugs",
            "медикаменты",
            "medicine",
            "медицина",
            "hospital",
            "больница",
            "clinic",
            "клиника",
            "doctor",
            "врач",
            "dentist",
            "стоматолог",
            "health",
            "здоровье"
        ),
        Category.FOOD to listOf(
            "supermarket",
            "супермаркет",
            "grocery",
            "продукты",
            "restaurant",
            "ресторан",
            "cafe",
            "кафе",
            "fast food",
            "фастфуд",
            "dining",
            "обед",
            "еда",
            "food",
            "продукты питания",
            "пицца",
            "пекарня",
            "bakery",
            "пиццерия",
            "pizzeria",
            "мороженое",
            "ice cream",
            "bar",
            "бар",
            "pub",
            "паб",
            "bistro",
            "бистро"
        ),
        Category.FUEL to listOf(
            "gas station",
            "заправка",
            "fuel",
            "бензин",
            "diesel",
            "дизель",
            "petrol",
            "газ",
            "oil",
            "масло",
            "автозаправка",
            "АЗС",
            "gasoline",
            "заправка",
            "fueling",
            "refueling",
            "заправочная станция"
        ),
        Category.SPORT to listOf(
            "gym",
            "спортзал",
            "fitness",
            "фитнес",
            "sport",
            "спорт",
            "workout",
            "тренировка",
            "yoga",
            "йога",
            "спортклуб",
            "sports",
            "athletics",
            "атлетика",
            "swimming",
            "плавание",
            "cycling",
            "велоспорт",
            "running",
            "бег",
            "marathon",
            "марафон"
        ),
        Category.EVENTS to listOf(
            "concert",
            "концерт",
            "festival",
            "фестиваль",
            "event",
            "мероприятие",
            "theater",
            "театр",
            "cinema",
            "кино",
            "exhibition",
            "выставка",
            "show",
            "шоу",
            "performance",
            "представление",
            "opera",
            "опера",
            "ballet",
            "балет"
        ),
        Category.TRANSPORT to listOf(
            "taxi",
            "такси",
            "bus",
            "автобус",
            "train",
            "поезд",
            "metro",
            "метро",
            "flight",
            "рейс",
            "plane",
            "самолет",
            "transport",
            "транспорт",
            "car rental",
            "аренда автомобиля",
            "rent a car",
            "арендовать машину",
            "ferry",
            "паром"
        ),
        Category.ENTERTAINMENT to listOf(
            "movie",
            "кино",
            "game",
            "игра",
            "music",
            "музыка",
            "party",
            "вечеринка",
            "entertainment",
            "развлечения",
            "club",
            "клуб",
            "nightclub",
            "ночной клуб",
            "concert",
            "концерт",
            "festival",
            "фестиваль",
            "fun",
            "развлечение"
        ),
        Category.UTILITIES to listOf(
            "electricity",
            "электричество",
            "water",
            "вода",
            "gas",
            "газ",
            "internet",
            "интернет",
            "utilities",
            "коммунальные услуги",
            "heating",
            "отопление",
            "cooling",
            "кондиционирование",
            "trash",
            "мусор",
            "sewer",
            "канализация",
            "utility bill",
            "коммунальный счет"
        ),
        Category.SHOPPING to listOf(
            "mall",
            "торговый центр",
            "clothes",
            "одежда",
            "shopping",
            "покупки",
            "store",
            "магазин",
            "electronics",
            "электроника",
            "furniture",
            "мебель",
            "appliances",
            "бытовая техника",
            "books",
            "книги",
            "shoes",
            "обувь",
            "accessories",
            "аксессуары"
        ),
        Category.TRAVEL to listOf(
            "hotel",
            "отель",
            "flight",
            "перелет",
            "travel",
            "путешествие",
            "vacation",
            "отпуск",
            "tour",
            "тур",
            "accommodation",
            "жилье",
            "resort",
            "курорт",
            "cruise",
            "круиз",
            "tourism",
            "туризм",
            "visa",
            "виза",
            "passport",
            "паспорт"
        )
    )

    fun mapToCategorizePurchase(place: String): Category {
        for ((category, keywords) in categoryToKeywords) {
            if (keywords.any { keyword -> place.contains(keyword, ignoreCase = true) }) {
                return category
            }
        }
        return Category.OTHER
    }
}