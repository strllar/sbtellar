object stelalr_core_cfg_tpl {

  val generalConfig =
    """
      |PEER_PORT= 11625
      |HTTP_PORT=11626
      |PUBLIC_HTTP_PORT=false
      |#DATABASE="postgresql://dbname=stellar user=postgres password=password host=localhost"
      |#DATABASE="sqlite3://:memory:"
      |DATABASE="sqlite3://./stellar.db"
      |
      |LOG_FILE_PATH="stellar-core.log"
      |TMP_DIR_PATH="tmp"
      |BUCKET_DIR_PATH="buckets"
      |""".stripMargin

  val setStandAlone =
    """
      |RUN_STANDALONE=true
    |""".stripMargin

  val setCompleteCatchup =
  """
    |CATCHUP_COMPLETE=true
  |""".stripMargin

  def validatorSeed(seed:String) =
  s"""
    |VALIDATION_SEED="$seed"
  |""".stripMargin

  def secureSetting(safe:Boolean, maxfailure:Int) =
    s"""
       |UNSAFE_QUORUM=${!safe}
       |FAILURE_SAFETY=$maxfailure
    |""".stripMargin

  def localHistory(name:String = "local", urlprefix:String = "hist") =
    s"""
       |[HISTORY.$name]
       |get="../bin/curl -sf http://localhost:8080/$urlprefix/{0} -o {1} "
       |put="../bin/curl -sf -X PUT --data-binary @{0} http://localhost:8080/$urlprefix/{1}"
    |""".stripMargin

  //  val nativeHistory =
  //  """
  //    |[HISTORY.vs]
  //    |get="cp /tmp/stellar-core/history/vs/{0} {1}"
  //    |put="cp {0} /tmp/stellar-core/history/vs/{1}"
  //    |mkdir="mkdir -p /tmp/stellar-core/history/vs/{0}"
  //  """.stripMargin

  val testnetPeers =
    """
      |KNOWN_PEERS=[
      |"core-testnet1.stellar.org",
      |"core-testnet2.stellar.org",
      |"core-testnet3.stellar.org"]
    |""".stripMargin

  val testnetQSET =
    """
      |#The public keys of the Stellar testnet servers
      |[QUORUM_SET]
      |VALIDATORS=[
      |"GDKXE2OZMJIPOSLNA6N6F2BVCI3O777I2OOC4BV7VOYUEHYX7RTRYA7Y",
      |"GCUCJTIYXSOXKBSNFGNFWW5MUQ54HKRPGJUTQFJ5RQXZXNOLNXYDHRAP",
      |"GC2V2EFSXN6SQTWVYA5EPJPBWWIMSD2XQNKUOHGEKB535AQE2I6IXV2Z"]
    |""".stripMargin

  val testnetHistory =
    """
      |#The history store of the Stellar testnet
      |[HISTORY.h1]
      |get="curl -sf https://s3-eu-west-1.amazonaws.com/history.stellar.org/prd/core-testnet/core-testnet-001/{0} -o {1}"
      |
      |[HISTORY.h2]
      |get="curl -sf https://s3-eu-west-1.amazonaws.com/history.stellar.org/prd/core-testnet/core-testnet-002/{0} -o {1}"
      |
      |[HISTORY.h3]
      |get="curl -sf https://s3-eu-west-1.amazonaws.com/history.stellar.org/prd/core-testnet/core-testnet-003/{0} -o {1}"
    |""".stripMargin

  val fullExample =
    """
      |#
      |#  This file gives details of the various configuration parameters you can set
      |#  when running stellar-core. You will need to edit to fit your own set up.
      |#
      |# This file is a TOML file. See https://github.com/toml-lang/toml for syntax.
      |
      |
      |###########################
      |## General admin settings
      |
      |
      |# LOG_FILE_PATH (string) default "stellar-core.log"
      |# Path to the file you want stellar-core to write its log to.
      |# You can set to "" for no log file.
      |LOG_FILE_PATH=""
      |
      |# TMP_DIR_PATH (string) default "tmp"
      |# Specifies the directory where stellar-core should store its temporary files.
      |TMP_DIR_PATH="tmp"
      |
      |
      |# BUCKET_DIR_PATH (string) default "buckets"
      |# Specifies the directory where stellar-core should store the bucket list.
      |# This will get written to a lot and will grow as the size of the ledger grows.
      |BUCKET_DIR_PATH="buckets"
      |
      |
      |# DATABASE (string) default "sqlite3://:memory:"
      |# Sets the DB connection string for SOCI.
      |# Defaults to an in memory database.
      |# If using sqlite, a string like:
      |#
      |#   "sqlite3://path/to/dbname.db"
      |#
      |# alternatively, if using postgresql, a string like:
      |#
      |#   "postgresql://dbname=stellar user=xxxx password=yyyy host=10.0.x.y"
      |#
      |# taking any combination of parameters from:
      |#
      |#   http://www.postgresql.org/docs/devel/static/libpq-connect.html#LIBPQ-PARAMKEYWORDS
      |#
      |DATABASE="sqlite3://stellar.db"
      |
      |
      |# HTTP_PORT (integer) default 11626
      |# What port stellar-core listens for commands on.
      |HTTP_PORT=11626
      |
      |# PUBLIC_HTTP_PORT (true or false) default false
      |# If false you only accept stellar commands from localhost.
      |# Do not set to true and expose the port to the open internet. This will allow
      |#  random people to run stellar commands on your server. (such as `stop`)
      |PUBLIC_HTTP_PORT=false
      |
      |# COMMANDS  (list of strings) default is empty
      |# List of commands to run on startup.
      |# Right now only setting log levels really makes sense.
      |COMMANDS=[
      |"ll?level=info&partition=Herder"
      |]
      |
      |
      |
      |###########################
      |## Overlay configuration
      |
      |# PEER_SEED (string) default empty
      |# If you don't set a PEER_SEED one will be generated for you randomly.
      |# PEER_SEED is what generates the peerID (used for peer connections) for this node
      |# In general can be left blank. Should be kept unique if you set it.
      |PEER_SEED="SDVASBVCLTGCDXD2C3O3BDI4EYF22ZXCNOBSEKHWSQQLXA4AZF5VZUJI"
      |
      |# PEER_PORT (Integer) defaults to 11625
      |# The port other instances of stellar-core can connect to you on.
      |PEER_PORT=11625
      |
      |# TARGET_PEER_CONNECTIONS (Integer) default 20
      |# This server will send outbound connection attempts until it is at this
      |#   number of peer connections.
      |TARGET_PEER_CONNECTIONS=20
      |
      |# MAX_PEER_CONNECTIONS (Integer) default 50
      |# This server will start dropping peers if it is above this number of
      |#  connected peers.
      |MAX_PEER_CONNECTIONS=30
      |
      |# PREFERRED_PEERS (list of strings) default is empty
      |# These are IP:port strings that this server will add to its DB of peers.
      |# This server will try to always stay connected to the other peers on this list.
      |PREFERRED_PEERS=["127.0.0.1:7000","127.0.0.1:8000"]
      |
      |# KNOWN_PEERS (list of strings) default is empty
      |# These are IP:port strings that this server will add to its DB of peers.
      |# It will try to connect to these when it is below TARGET_PEER_CONNECTIONS.
      |KNOWN_PEERS=[
      |"core-testnet1.stellar.org",
      |"core-testnet2.stellar.org",
      |"core-testnet3.stellar.org"]
      |
      |
      |
      |#######################
      |##  SCP settings
      |
      |
      |# VALIDATION_SEED (string) default empty
      |# The seed for generating the nodeID used in SCP. Your seed should be unique.
      |# Protect your seed. Treat it like a password.
      |# Only nodes that want to participate in SCP need to set VALIDATION_SEED.
      |# Most instances should operate in observer mode without a seed set here.
      |VALIDATION_SEED="SBI3CZU7XZEWVXU7OZLW5MMUQAP334JFOPXSLTPOH43IRTEQ2QYXU5RG"
      |
      |
      |# See QUORUM_SET below
      |
      |
      |# DESIRED_BASE_FEE (integer) default 10
      |# This is what you would prefer the base fee to be. It is in stroops.
      |DESIRED_BASE_FEE=10
      |
      |# DESIRED_BASE_RESERVE (integer) default 10000000
      |# This is what you prefer the base reserve to be. It is in stroops.
      |DESIRED_BASE_RESERVE=10000000
      |
      |# DESIRED_MAX_TX_PER_LEDGER (integer) default 500
      |# This is how many maximum transactions per ledger you would like to process.
      |# You will not nominate a transaction set with more transactions than this.
      |# If this maximum is reached you will include the transactions that are paying
      |#  a higher relative fee and wait to include transactions with lower fees until
      |#  there is less load on the network.
      |# You will still attempt to apply transaction sets that the network comes to
      |#  consensus about that conatin more transactions than this.
      |DESIRED_MAX_TX_PER_LEDGER=400
      |
      |# FAILURE_SAFETY (interger) default 1
      |# This is the number of failures you want to be able to tolerate.
      |# You will need at least 3f+1 nodes in your quorum set.
      |# If you don't have enough in your quorum set to tolerate the level you
      |#  set here stellar-core won't run.
      |FAILURE_SAFETY=1
      |
      |# UNSAFE_QUORUM (true or false) default false
      |# If set to true allows you to specify an unsafe quorum set.
      |# Otherwise it won't start if you have your threshold % set too low.
      |# You might want to set this if you are running your own network and
      |#  aren't concerned with byzantine failures.
      |UNSAFE_QUORUM=false
      |
      |
      |#########################
      |##  History
      |
      |
      |# CATCHUP_COMPLETE (true or false) defaults to false
      |# if true will catchup to the network "completely" (replaying all history)
      |# if false will catchup "minimally", using deltas to the most recent snapshot.
      |CATCHUP_COMPLETE=false
      |
      |# MAX_CONCURRENT_SUBPROCESSES (integer) default 8
      |# History catchup can potentialy spawn a bunch of sub-processes.
      |# This limits the number that will be active at a time.
      |MAX_CONCURRENT_SUBPROCESSES=10
      |
      |
      |
      |# See HISTORY table at below
      |
      |
      |
      |###############################
      |## The following options should probably never be set. They are used primarily
      |##  for testing.
      |
      |# RUN_STANDALONE (true or false) defaults to false
      |# This is a mode for testing. It prevents you from trying to connect
      |#  to other peers
      |RUN_STANDALONE=false
      |
      |# PARANOID_MODE (true or false) defaults to false
      |# Setting this will cause all sorts of extra checks to occur.
      |# the overhead may cause slower systems to not perform as fast as the rest
      |#   of the network, caution is advised when using this.
      |PARANOID_MODE=false
      |
      |
      |# MANUAL_CLOSE (true or false) defaults to false
      |# Mode for testing. Ledger will only close when stellar-core gets
      |#  the `manualclose` command
      |MANUAL_CLOSE=false
      |
      |
      |# ARTIFICIALLY_GENERATE_LOAD_FOR_TESTING (true or false) defaults to false
      |# Enables synthetic load generation on demand.
      |# The load is triggered by the `generateload` runtime command.
      |# This option only exists for stress-testing and should not be enabled in
      |#  production networks.
      |ARTIFICIALLY_GENERATE_LOAD_FOR_TESTING=false
      |
      |
      |# ARTIFICIALLY_ACCELERATE_TIME_FOR_TESTING (true or false) defaults to false
      |# Reduces ledger close time to 1s and checkpoint frequency to every 8 ledgers.
      |# Do not ever set this in production, as it will make your history archives
      |#   incompatible with those of anyone else.
      |ARTIFICIALLY_ACCELERATE_TIME_FOR_TESTING=false
      |
      |
      |# ARTIFICIALLY_PESSIMIZE_MERGES_FOR_TESTING (true or false) defaults to false
      |# Avoids resolving FutureBuckets before writing them to the database's
      |#  persistent state. This option exists only for stress-testing the ability to
      |#  resume from an interrupted merge, and should be false in all normal cases.
      |ARTIFICIALLY_PESSIMIZE_MERGES_FOR_TESTING=false
      |
      |
      |#####################
      |##  Tables must come at the end. (TOML you are almost perfect!)
      |
      |# HISTORY
      |# Used to specify where to fetch and store the history archives.
      |# Fetching and storing history is kept as general as possible.
      |# Any place you can save and load static files from should be usable by the
      |#  stellar-core history system.   s3, the file system, http, etc
      |# stellar-core will call any external process you specify and will pass it the
      |#  name of the file to save or load.
      |# Simply use template parameters `{0}` and `{1}` in place of the files being transmitted or retrieved.
      |# You can specify multiple places to store and fetch from. stellar-core will
      |# use multiple fetching locations as backup in case there is a failure fetching from one.
      |[HISTORY.vs]
      |get="cp /tmp/stellar-core/history/vs/{0} {1}"
      |put="cp {0} /tmp/stellar-core/history/vs/{1}"
      |mkdir="mkdir -p /tmp/stellar-core/history/vs/{0}"
      |
      |# other examples:
      |# [HISTORY.stellar]
      |# get="curl http://history.stellar.org/{0} -o {1}"
      |# put="aws s3 cp {0} s3://history.stellar.org/{1}"
      |
      |# [HISTORY.backup]
      |# get="curl http://backupstore.blob.core.windows.net/backupstore/{0} -o {1}"
      |# put="azure storage blob upload {0} backupstore {1}"
      |
      |#The history store of the Stellar testnet
      |#[HISTORY.h1]
      |#get="curl -sf https://s3-eu-west-1.amazonaws.com/history.stellar.org/prd/core-testnet/core-testnet-001/{0} -o {1}"
      |
      |#[HISTORY.h2]
      |#get="curl -sf https://s3-eu-west-1.amazonaws.com/history.stellar.org/prd/core-testnet/core-testnet-002/{0} -o {1}"
      |
      |#[HISTORY.h3]
      |#get="curl -sf https://s3-eu-west-1.amazonaws.com/history.stellar.org/prd/core-testnet/core-testnet-003/{0} -o {1}"
      |
      |
      |# QUORUM_SET (object see below) default is empty
      |# This is how you specify this server's quorom set.
      |# It can be nested 2 levels: (A,B,C,(D,E,F),(G,H,(I,J,K,L)))
      |# The THRESHOLD_PERCENT is how many have to agree. The sets are treated
      |# as one vote. So for example in the above there are 5 things that can vote:
      |# individual validators: A,B,C and sets (D,E,F) and set (G,H with subset (I,J,K,L))
      |# the sets each have their own threshold.
      |# This allows you to for example, treat 3 servers of some enitity as one vote
      |#  but you only need to hear from 2 of them.
      |# THRESHOLD_PERCENT defaults to 67.
      |# The following setup is equivalent to the example given above.
      |[QUORUM_SET]
      |VALIDATORS=[
      |"GDQWITFJLZ5HT6JCOXYEVV5VFD6FTLAKJAUDKHAV3HKYGVJWA2DPYSQV",
      |"GANLKVE4WOTE75MJS6FQ73CL65TSPYYMFZKC4VDEZ45LGQRCATGAIGIA",
      |"GDV46EIEF57TDL4W27UFDAUVPDDCKJNVBYB3WIV2WYUYUG753FCFU6EJ"]
      |[QUORUM_SET.1]
      |THRESHOLD_PERCENT=67
      |VALIDATORS=[
      |"GDKYAJOBUIXSFGGE3EPBSGZD7JT2YKMFLELG6A27LUCZWH4T52TPP6LH",
      |"GDXJAZZJ3H5MJGR6PDQX3JHRREAVYNCVM7FJYGLZJKEHQV2ZXEUO5SX2",
      |"GB6GK3WWTZYY2JXWM6C5LRKLQ2X7INQ7IYTSECCG3SMZFYOZNEZR4SO5"]
      |[QUORUM_SET.2]
      |THRESHOLD_PERCENT=100
      |VALIDATORS=[
      |"GCTAIXWDDBM3HBDHGSAOLY223QZHPS2EDROF7YUBB3GNYXLOCPV5PXUK",
      |"GCJ6UBAOXNQFN3HGLCVQBWGEZO6IABSMNE2OCQC4FJAZXJA5AIE7WSPW"]
      |[QUORUM_SET.2.1]
      |THRESHOLD_PERCENT=50
      |VALIDATORS=[
      |"GC4X65TQJVI3OWAS4DTA2EN2VNZ5ZRJD646H5WKEJHO5ZHURDRAX2OTH",
      |"GAXSWUO4RBELRQT5WMDLIKTRIKC722GGXX2GIGEYQZDQDLOTINQ4DX6F",
      |"GAWOEMG7DQDWHCFDTPJEBYWRKUUZTX2M2HLMNABM42G7C7IAPU54GL6X",
      |"GDZAJNUUDJFKTZX3YWZSOAS4S4NGCJ5RQAY7JPYBG5CUFL3JZ5C3ECOH"]
      |
    |""".stripMargin
}