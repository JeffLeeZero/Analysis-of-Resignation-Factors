from pyquery import PyQuery as pq
import cx_Oracle as oracle


def get_page(url):
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36',
        'Cookie': '_xsrf=P7U0b3BzG3yZf2Tji1Ls0pynYHYsrx65; _zap=b3b2a98e-f0d0-4ca3-8a01-69d324dee824; d_c0="AGCunJy2sQ-PTrfhi04967rZCHOfnhcYBWU=|1562404177"; tst=r; q_c1=103178a161214b52939307a6d01af24d|1562464657000|1562464657000; tgw_l7_route=80f350dcd7c650b07bd7b485fcab5bf7; capsion_ticket="2|1:0|10:1562546778|14:capsion_ticket|44:NmRhNzZjNjI5ZDIyNDhhODgwZjU1OWMxOGY2NTY5YzI=|f0ad9817a6cd65f54e25a9a0d325c1f296e242648032950bd2c73b90e28a6895'
    }
    doc = pq(url, headers=headers)

    return doc

def get_answer(doc):
    content = doc('.List-item')
    result = []
    for i in content:
        answer = pq(i)

        author_start = answer('.ContentItem.AnswerItem').attr('data-zop').index(":")+2
        author_end = answer('.ContentItem.AnswerItem').attr('data-zop').index(",")-1

        title_start = answer('.ContentItem.AnswerItem').attr('data-zop').index("title")+8
        title_end = answer('.ContentItem.AnswerItem').attr('data-zop').index("type")-3

        item = {
            'title':answer('.ContentItem.AnswerItem').attr('data-zop')[title_start:title_end],
            'author':answer('.ContentItem.AnswerItem').attr('data-zop')[author_start:author_end],
            'content':answer('.RichText.ztext.CopyrightRichText-richText').text(),
        }

        result.append(item)
    return result

def import_answer(result):
    #db = oracle.connect('admin/123456@localhost/orcl')
    db = oracle.connect('FRANK/ZD73330274@localhost/orcl')
    cursor = db.cursor()

    sql1 = "select title, author from answer"
    cursor.execute(sql1)
    db.commit()
    match = cursor.fetchall()
    title_list = [str(j[0]) for j in match]
    author_list = [str(j[1]) for j in match]

    for j in result:
        if(j['author'] in author_list and j['title'] in title_list):
            continue
        else:
            sql = "insert into answer (title, author, content) VALUES (:1, :2,:3)"
            para = [j['title'],j['author'],j['content']]
            cursor.execute(sql, para)
            db.commit()
    cursor.close()
    db.close()

def get_column(doc):
    article = doc('.Post-content')

    author_start = article('.Post-content').attr('data-zop').index(":") + 2
    author_end = article('.Post-content').attr('data-zop').index(",") - 1

    result = []
    item = {
        'title': article('.Post-Title').text(),
        'author': article('.Post-content').attr('data-zop')[author_start:author_end],
        'content': article('.Post-RichTextContainer p').text(),
    }
    result.append(item)
    return result

def main():
    results = []

    doc1 = get_page('https://www.zhihu.com/question/295602067')
    doc2 = get_page('https://www.zhihu.com/question/61836622')
    doc3 = get_page('https://zhuanlan.zhihu.com/p/44648151')
    doc4 = get_page('https://zhuanlan.zhihu.com/p/70145450')
    doc5 = get_page('https://zhuanlan.zhihu.com/p/28355608')
    doc6 = get_page('https://zhuanlan.zhihu.com/p/42602201')
    doc7 = get_page('https://www.zhihu.com/question/29671468')
    doc8 = get_page('https://zhuanlan.zhihu.com/p/42652323')
    doc9 = get_page('https://zhuanlan.zhihu.com/p/32767077')
    doc10 = get_page('https://zhuanlan.zhihu.com/p/33387057')

    results.append(get_answer(doc1))
    results.append(get_answer(doc2))
    results.append(get_column(doc3))
    results.append(get_column(doc4))
    results.append(get_column(doc5))
    results.append(get_column(doc6))
    results.append(get_answer(doc7))
    results.append(get_column(doc8))
    results.append(get_column(doc9))
    results.append(get_column(doc10))
    for i in results:
        import_answer(i)


if __name__ == '__main__':
    main()