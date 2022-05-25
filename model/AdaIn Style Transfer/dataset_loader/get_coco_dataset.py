import fiftyone as fo
import fiftyone.zoo as foz

''' We wont use COCO as main data set because it's a very big dataset,
then only the validation set will be used to add train, val and test dataset on main VOC Pascal dataset'''

# Change split method between 'train', 'test' or 'validation'
dataset = foz.load_zoo_dataset("coco-2017", split="validation")
