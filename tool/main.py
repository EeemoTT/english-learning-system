import json
import xlwt

file = open('/Users/eeemot_t/Downloads/IELTSluan_2.json', 'r',encoding='utf-8')
id = []
word = []
book = []
ukphone = []
tran = []

for line in file.readlines():
    words = line.strip();
    data = json.loads(line);
    id.append(data["wordRank"])
    word.append(data["headWord"])
    book.append(data["bookId"])
    if"ukphone" in data["content"]["word"]["content"]:
        ukphone.append(data["content"]["word"]["content"]["ukphone"])

    else:
        ukphone.append("null")
    tran.append(data["content"]["word"]["content"]["trans"][0]["tranCn"])



file = xlwt.Workbook('encoding = utf-8')
sheet1 = file.add_sheet('sheet1',cell_overwrite_ok=True)
sheet1.write(0, 0, "word")
sheet1.write(0, 1, "ukphone")
sheet1.write(0, 2, "tran")
sheet1.write(0, 3, "book")

for i in range(len(word)):
    sheet1.write(i + 1, 0, word[i])
    sheet1.write(i + 1, 1, ukphone[i])
    sheet1.write(i + 1, 2, tran[i])
    sheet1.write(i + 1, 3, book[i])


print("saving---------------------")

file.save('IELTS2.xls')