create trigger friend_update_trigger
  after UPDATE
  on userinfo
  for each row
  BEGIN
	UPDATE friend SET friend.Photo=userinfo.Photo,friend.Signature=userinfo.Signature,friend.NowScenery=userinfo.NowScenery WHERE userinfo.UserId=friend.TargetId;
END;

