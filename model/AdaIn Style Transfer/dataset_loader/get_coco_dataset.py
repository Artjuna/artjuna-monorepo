import fiftyone as fo
import fiftyone.zoo as foz

# Change split method between 'train', 'test' or 'validation'
dataset = foz.load_zoo_dataset("coco-2017", split="validation")
