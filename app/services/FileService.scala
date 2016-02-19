package services


import java.io.InputStream
import models.{UUID, Dataset, File, Comment}
import securesocial.core.Identity
import com.mongodb.casbah.Imports._
import play.api.libs.json.{JsObject, JsArray, JsValue}

/**
 * Generic file service to store blobs of files and metadata about them.
 *
 * @author Luigi Marini
 *
 */
trait FileService {
  /**
   * The number of files
   */
  def count(): Long

  /**
   * Save a file from an input stream.
   */
  def save(inputStream: InputStream, filename: String, contentType: Option[String], author: Identity, showPreviews: String = "DatasetLevel"): Option[File]

  /**
   * Get the input stream of a file given a file id.
   * Returns input stream, file name, content type, content length.
   */
  def getBytes(id: UUID): Option[(InputStream, String, String, Long)]

  /**
   * Remove the file from mongo
   */
  def removeFile(id: UUID)

  /**
   * List all files in the system.
   */
  def listFiles(): List[File]

  /**
   * List all files in the system that are not intermediate result files generated by the extractors.
   */
  def listFilesNotIntermediate(): List[File]

  /**
   * List files after a specified date.
   */
  def listFilesAfter(date: String, limit: Int): List[File]

  /**
   * List files before a specified date.
   */
  def listFilesBefore(date: String, limit: Int): List[File]

  /**
   * List files for a specific user after a specified date.
   */
  def listUserFilesAfter(date: String, limit: Int, email: String): List[File]

  /**
   * List files for a specific user before a specified date.
   */
  def listUserFilesBefore(date: String, limit: Int, email: String): List[File]

  /**
   * Get file metadata.
   */
  def get(id: UUID): Option[File]

  /**
   * Lastest file in chronological order.
   */
  def latest(): Option[File]

  /**
   * Lastest x files in chronological order.
   */
  def latest(i: Int): List[File]

  /**
   * First file in chronological order.
   */
  def first(): Option[File]

  def index(id: UUID)

  /**
   * Return a list of tags and counts found in sections
   */
  def getTags(): Map[String, Long]

  /**
   * Update thumbnail used to represent this dataset.
   */
  def updateThumbnail(fileId: UUID, thumbnailId: UUID)

  // TODO return JsValue
  def getXMLMetadataJSON(id: UUID): String

  def modifyRDFOfMetadataChangedFiles()

  def modifyRDFUserMetadata(id: UUID, mappingNumber: String="1")

  def dumpAllFileMetadata(): List[String]

  def isInDataset(file: File, dataset: Dataset): Boolean

  def removeTags(id: UUID, userIdStr: Option[String], eid: Option[String], tags: List[String])

  def addMetadata(fileId: UUID, metadata: JsValue)

  def listOutsideDataset(dataset_id: UUID): List[File]

  def getMetadata(id: UUID): scala.collection.immutable.Map[String,Any]

  def getUserMetadata(id: UUID): scala.collection.mutable.Map[String,Any]

  def getUserMetadataJSON(id: UUID): String

  def getTechnicalMetadataJSON(id: UUID): String

  def getVersusMetadata(id:UUID): Option[JsValue]

  def addVersusMetadata(id: UUID, json: JsValue)

  def getJsonArray(list: List[JsObject]): JsArray

  def addUserMetadata(id: UUID, json: String)

  def addXMLMetadata(id: UUID, json: String)

  def findByTag(tag: String): List[File]

  def findByTag(tag: String, start: String, limit: Integer, reverse: Boolean): List[File]

  def findIntermediates(): List[File]

  def addTags(id: UUID, userIdStr: Option[String], eid: Option[String], tags: List[String])

  def removeAllTags(id: UUID)

  def comment(id: UUID, comment: Comment)

  def setIntermediate(id: UUID)

  def renameFile(id: UUID, newName: String)

  def setContentType(id: UUID, newType: String)

  def setUserMetadataWasModified(id: UUID, wasModified: Boolean)

  def removeTemporaries()

  def findMetadataChangedFiles(): List[File]

  def searchAllMetadataFormulateQuery(requestedMetadataQuery: Any): List[File]

  def searchUserMetadataFormulateQuery(requestedMetadataQuery: Any): List[File]

  def searchMetadataFormulateQuery(requestedMap: java.util.LinkedHashMap[String, Any], root: String): MongoDBObject

  def removeOldIntermediates()

  /**
   * Update the license data that is currently associated with the file.
   *
   * id: The id of the file
   * licenseType: A String representing the type of license
   * rightsHolder: A String that is the free-text describing the owner of the license. Only required for certain license types
   * licenseText: Text that describes what the license is
   * licenseUrl: A reference to the license information
   * allowDownload: true or false, to allow downloading of the file or dataset. Relevant only for certain license types
   */
  def updateLicense(id: UUID, licenseType: String, rightsHolder: String, licenseText: String, licenseUrl: String, allowDownload: String)

  def setNotesHTML(id: UUID, notesHTML: String)

  /**
   * Add follower to a file.
   */
  def addFollower(id: UUID, userId: UUID)

  /**
   * Remove follower from a file.
   */
  def removeFollower(id: UUID, userId: UUID)

  /**
   * Update technical metadata
   */
  def updateMetadata(fileId: UUID, metadata: JsValue, extractor_id: String)

  def updateDescription(fileId : UUID, description : String)

}
