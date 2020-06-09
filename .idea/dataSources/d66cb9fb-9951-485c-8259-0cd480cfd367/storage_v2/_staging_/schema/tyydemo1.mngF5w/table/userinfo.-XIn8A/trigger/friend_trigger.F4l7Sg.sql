create trigger friend_trigger
  before UPDATE
  on userinfo
  for each row
  BEGIN
UPDATE friend SET friend.Signature=userinfo.Signature,friend.Photo=userinfo.Photo,friend.NowScenery=userinfo.NowScenery WHERE userinfo.UserId=friend.TargetId;
END;

