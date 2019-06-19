.class public Lthe_machine/Kik;
.super Ljava/lang/Object;
.source "Kik.java"

# direct methods
.method private constructor <init>()V
    .locals 0

    .prologue
    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static addUser(Lthe_machine/User;Lthe_machine/Group;)V
    .locals 5

    invoke-static {}, Lkik/machine/chat/KikApplication;->GetKikCoreFH()Lkik/core/f/h;

    move-result-object v0

    #v1 is our ArrayList object. Pass it in to List in Lkik/core/g/f/r function
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    invoke-virtual {p0}, Lthe_machine/User;->getJID()Ljava/lang/String;

    #v2 holds our user jid. We add this register to the java List object.
    move-result-object v2

    invoke-virtual {p1}, Lthe_machine/Group;->getJID()Ljava/lang/String;

    #v3 holds our group jid. Pass it to String in Lkik/core/g/f/r function
    move-result-object v3

    #Now that we have our strings. Add the userJID to the list

    invoke-virtual {v1, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v4, Lkik/core/g/f/r;

    invoke-direct {v4, v3, v1}, Lkik/core/g/f/r;-><init>(Ljava/lang/String;Ljava/util/List;)V

    invoke-interface {v0, v4}, Lkik/core/f/h;->a(Lkik/core/g/f/af;)Lcom/kik/f/k;

    move-result-object v0

    return-void
.end method

.method public static banUser(Lthe_machine/User;Lthe_machine/Group;)V
    .locals 3

    invoke-static {}, Lkik/machine/chat/KikApplication;->GetKikCoreFH()Lkik/core/f/h;

    move-result-object v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lthe_machine/User;->getJID()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1}, Lthe_machine/Group;->getJID()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lkik/core/g/f/x;->b(Ljava/lang/String;Ljava/lang/String;)Lkik/core/g/f/x;

    move-result-object v1

    invoke-interface {v0, v1}, Lkik/core/f/h;->a(Lkik/core/g/f/af;)Lcom/kik/f/k;

    move-result-object v0

    :cond_0
    return-void
.end method

.method public static unbanUser(Lthe_machine/User;Lthe_machine/Group;)V
    .locals 5

    invoke-static {}, Lkik/machine/chat/KikApplication;->GetKikCoreFH()Lkik/core/f/h;

    move-result-object v0

    invoke-virtual {p0}, Lthe_machine/User;->getJID()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1}, Lthe_machine/Group;->getJID()Ljava/lang/String;

    move-result-object v2

    const/4 v3, 0x0

    const/4 v4, 0x0

    invoke-interface {v0, v1, v2, v3, v4}, Lkik/core/f/n;->a(Ljava/lang/String;Ljava/lang/String;ZZ)Lcom/kik/f/k;



    move-result-object v0

    return-void
.end method

.method public static getGroupMembers(Lthe_machine/Group;)[Lthe_machine/User;
    .locals 1
    .param p0, "group"    # Lthe_machine/Group;

    .prologue
    .line 29
    const/4 v0, 0x0

    return-object v0
.end method

.method public static getKikApplication()Landroid/app/Application;
    .locals 1

    .prologue
    .line 72
    invoke-static {}, Lkik/machine/chat/KikApplication;->GetKikApplication()Lkik/machine/chat/KikApplication;

    move-result-object v0

    check-cast v0, Landroid/app/Application;

    return-object v0
.end method

.method public static kickUser(Lthe_machine/User;Lthe_machine/Group;)V
    .locals 3

    invoke-static {}, Lkik/machine/chat/KikApplication;->GetKikCoreFH()Lkik/core/f/h;

    move-result-object v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lthe_machine/User;->getJID()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1}, Lthe_machine/Group;->getJID()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lkik/core/g/f/x;->a(Ljava/lang/String;Ljava/lang/String;)Lkik/core/g/f/x;

    move-result-object v1

    invoke-interface {v0, v1}, Lkik/core/f/h;->a(Lkik/core/g/f/af;)Lcom/kik/f/k;

    :cond_0
    return-void
.end method

# Works
.method public static messageGroup(Ljava/lang/String;Lthe_machine/Group;)V
    .locals 3

    .prologue
    .line 8
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    .line 10
    .local v0, "newMessage":Ljava/lang/String;
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_0

    sget v1, Lkik/core/d/z$a;->DEFAULT$5ff67128:I

    invoke-virtual {p1}, Lthe_machine/Group;->getJID()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2, v1}, Lkik/core/d/z;->a(Ljava/lang/String;Ljava/lang/String;I)Lkik/core/d/z;

    move-result-object v0

    invoke-static {}, Lkik/machine/chat/KikApplication;->e()Lkik/machine/util/ay;

    move-result-object v1

    invoke-virtual {v1, v0}, Lkik/machine/util/ay;->a(Lkik/core/d/z;)V

    :cond_0
    return-void
.end method

.method public static messageUser(Ljava/lang/String;Lthe_machine/User;)V
    .locals 3
    .param p0, "message"    # Ljava/lang/String;
    .param p1, "user"    # Lthe_machine/User;

    .prologue
    .line 8
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    .line 10
    .local v0, "newMessage":Ljava/lang/String;
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_0

    sget v1, Lkik/core/d/z$a;->DEFAULT$5ff67128:I

    invoke-virtual {p1}, Lthe_machine/User;->getJID()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2, v1}, Lkik/core/d/z;->a(Ljava/lang/String;Ljava/lang/String;I)Lkik/core/d/z;

    move-result-object v0

    invoke-static {}, Lkik/machine/chat/KikApplication;->e()Lkik/machine/util/ay;

    move-result-object v1

    invoke-virtual {v1, v0}, Lkik/machine/util/ay;->a(Lkik/core/d/z;)V

    :cond_0
    return-void
.end method

.method public static leaveGroup(Lthe_machine/Group;)V
    .locals 3

    invoke-static {}, Lkik/machine/chat/KikApplication;->GetKikCoreFH()Lkik/core/f/h;

    move-result-object v0

    invoke-virtual {p0}, Lthe_machine/Group;->getJID()Ljava/lang/String;

    move-result-object v2

    new-instance v1, Lkik/core/g/f/w;

    invoke-direct {v1, v2}, Lkik/core/g/f/w;-><init>(Ljava/lang/String;)V

    invoke-interface {v0, v1}, Lkik/core/f/h;->a(Lkik/core/g/f/af;)Lcom/kik/f/k;

    move-result-object v0

    return-void
.end method


#p0 is new group name
#p1 is group jid we want to change

.method public static changeGroupName(Ljava/lang/String;Lthe_machine/Group;)V
    .locals 4

    invoke-static {}, Lkik/machine/chat/KikApplication;->GetKikCoreFH()Lkik/core/f/h;

    move-result-object v0

    invoke-virtual {p1}, Lthe_machine/Group;->getJID()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    new-instance v3, Lkik/core/g/f/s;

    #first parameter is group JID
    #second paramter is our new group name.

    invoke-direct {v3, v1, v2}, Lkik/core/g/f/s;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v3}, Lkik/core/f/h;->a(Lkik/core/g/f/af;)Lcom/kik/f/k;

    move-result-object v0

    :cond_0
    return-void
.end method


.method public static stopChatting(Lthe_machine/User;)V
    .locals 5

    invoke-virtual {p0}, Lthe_machine/User;->getJID()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string/jumbo v2, "@"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->indexOf(Ljava/lang/String;)I

    move-result v2

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v3

    invoke-virtual {v1, v2, v3}, Ljava/lang/StringBuilder;->delete(II)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    new-instance v1, Lkik/core/d/o;

    const-string v3, "talk.kik.com"

    const/4 v4, 0x0

    invoke-direct {v1, v2, v3, v4}, Lkik/core/d/o;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-static {}, Lkik/machine/chat/KikApplication;->GetKikCoreFY()Lkik/core/f/y;

    move-result-object v2

    invoke-interface {v2, v1}, Lkik/core/f/y;->b(Lkik/core/d/o;)Lcom/kik/f/k;

    move-result-object v0

    return-void
.end method