from dao.BaseDao import BaseDao


class OpiDao(BaseDao):
    def createJobData(self, sql, params):
        result = self.execute(sql, params)
        self.commit()
        return result
        pass

    def createcontent(self, data={}):
        sql = "insert into opinions (context,voteup_count,comment_count,questiontext) values (%s,%s,%s,%s)"
        params = [data.get('context'), data.get('voteup_count'), data.get('comment_count'), data.get('questiontext')]
        result = self.execute(sql, params)
        self.commit()
        return result
        pass
